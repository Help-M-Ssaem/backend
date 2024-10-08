package com.example.mssaembackendv2.domain.evaluation;

import com.example.mssaembackendv2.domain.badge.Badge;
import com.example.mssaembackendv2.domain.badge.BadgeEnum;
import com.example.mssaembackendv2.domain.badge.BadgeService;
import com.example.mssaembackendv2.domain.chatroom.ChatRoom;
import com.example.mssaembackendv2.domain.chatroom.ChatRoomRepository;
import com.example.mssaembackendv2.domain.dynamoChatConnections.ChatConnection;
import com.example.mssaembackendv2.domain.dynamoChatConnections.ChatConnectionService;
import com.example.mssaembackendv2.domain.evaluation.dto.EvaluationRequestDto.EvaluationInfo;
import com.example.mssaembackendv2.domain.evaluation.dto.EvaluationResultDto.EvaluationCount;
import com.example.mssaembackendv2.domain.evaluation.dto.EvaluationResultDto.EvaluationResult;
import com.example.mssaembackendv2.domain.member.Member;
import com.example.mssaembackendv2.domain.member.MemberRepository;
import com.example.mssaembackendv2.domain.notification.NotificationService;
import com.example.mssaembackendv2.domain.notification.NotificationType;
import com.example.mssaembackendv2.domain.worryboard.WorryBoard;
import com.example.mssaembackendv2.domain.worryboard.WorryBoardRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class EvaluationService {

    private final WorryBoardRepository worryBoardRepository;
    private final EvaluationRepository evaluationRepository;
    private final BadgeService badgeService;
    private final NotificationService notificationService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatConnectionService chatConnectionService;
    private final MemberRepository memberRepository;

    /**
     * 평가 추가 하기
     */
    public String insertEvaluation(Member member, EvaluationInfo evaluationInfo) {
        //현재 고민글 조회
        WorryBoard worryBoard = worryBoardRepository.findById(evaluationInfo.getWorryBoardId())
            .orElseThrow();
        //상대 조회
        ChatRoom chatRoom = chatRoomRepository.findChatRoomByWorryBoard(worryBoard);

        //평가 리스트 체크
        String[] checks = new String[5];
        Arrays.fill(checks, "0");
        evaluationInfo.getEvaluations().forEach(e -> {
            if (e.equals(EvaluationEnum.LIKE)) {
                checks[0] = "1";
            } else if (e.equals(EvaluationEnum.USEFUL)) {
                checks[1] = "1";
            } else if (e.equals(EvaluationEnum.FUN)) {
                checks[2] = "1";
            } else if (e.equals(EvaluationEnum.SINCERE)) {
                checks[3] = "1";
            } else if (e.equals(EvaluationEnum.HOT)) {
                checks[4] = "1";
            }
        });

        List<ChatConnection> connectionsByChatRoomId = chatConnectionService.getConnectionsByChatRoomId(String.valueOf(chatRoom.getId()));
        Long partnerId = 0L;

        for (ChatConnection chatConnection : connectionsByChatRoomId) {
            if(!chatConnection.getMemberId().equals(member.getId())) {
                partnerId = Long.valueOf(chatConnection.getMemberId());
                break;
            }
        }

        Member partner =  memberRepository.findById(partnerId).orElseThrow();

        String result = Arrays.stream(checks).collect(Collectors.joining());
        evaluationRepository.save(new Evaluation(worryBoard, partner, result));

        // Badge추가 및 알림 추가
        insertBadgeAndNotification(partner);
        return "펑가 완료";
    }

    /**
     * Badge 추가 및 알림 추가
     */
    public void insertBadgeAndNotification(Member partner) {
        EvaluationCount evaluationCount = countEvaluation(partner.getId());
        boolean check = true;
        if (badgeService.existBadgeStateTrue(partner)) {
            check = false;
        }
        if (evaluationCount.getFunCount() == BadgeEnum.FUNFUN.getStandard()) {
            Badge badge = new Badge(BadgeEnum.FUNFUN, partner, check);
            badgeService.insertBadge(badge);
            notificationService.createNotification(badge.getId(), badge.getBadgeEnum().getName(),
                NotificationType.BADGE, partner);
            if(check) partner.updateBadge(badge.getBadgeEnum().getName());
        } else if (evaluationCount.getHotCount() == BadgeEnum.MBTIRANO.getStandard()) {
            Badge badge = new Badge(BadgeEnum.MBTIRANO, partner, check);
            badgeService.insertBadge(badge);
            notificationService.createNotification(badge.getId(), badge.getBadgeEnum().getName(),
                NotificationType.BADGE, partner);
            if(check) partner.updateBadge(badge.getBadgeEnum().getName());
        } else if (evaluationCount.getUsefulCount() == BadgeEnum.MBTADULT.getStandard()) {
            Badge badge = new Badge(BadgeEnum.MBTADULT, partner, check);
            badgeService.insertBadge(badge);
            notificationService.createNotification(badge.getId(), badge.getBadgeEnum().getName(),
                NotificationType.BADGE, partner);
            if(check) partner.updateBadge(badge.getBadgeEnum().getName());
        } else if (evaluationCount.getSincereCount() == BadgeEnum.MBTMI.getStandard()) {
            Badge badge = new Badge(BadgeEnum.MBTMI, partner, check);
            badgeService.insertBadge(badge);
            notificationService.createNotification(badge.getId(), badge.getBadgeEnum().getName(),
                NotificationType.BADGE, partner);
            if(check) partner.updateBadge(badge.getBadgeEnum().getName());
        }
    }

    /**
     * 평가 받은 사람의 평가 내용 조회
     */
    public EvaluationResult selectEvaluation(Member member, Long worryBoardId) {
        //고민글 조회
        WorryBoard worryBoard = worryBoardRepository.findById(worryBoardId)
            .orElseThrow();
        Evaluation evaluation = evaluationRepository.findByWorryBoardAndMember(worryBoard, member);

        //자신이 받은 평가 출력
        List<EvaluationEnum> result = new ArrayList<>();
        EvaluationEnum[] enums = EvaluationEnum.values();
        for (int i = 0; i < evaluation.getEvaluationCode().length(); i++) {
            if (evaluation.getEvaluationCode().charAt(i) == '1') {
                result.add(enums[i]);
            }
        }
        return new EvaluationResult(worryBoardId, result);
    }

    /**
     * 자신의 평가 count
     */
    public EvaluationCount countEvaluation(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        List<Evaluation> evaluations = evaluationRepository.findAllByMember(member);
        int[] result = new int[EvaluationEnum.values().length];
        for (Evaluation e : evaluations) {
            char[] temp = e.getEvaluationCode().toCharArray();
            result[0] += temp[0] - '0';
            result[1] += temp[1] - '0';
            result[2] += temp[2] - '0';
            result[3] += temp[3] - '0';
            result[4] += temp[4] - '0';
        }
        return new EvaluationCount(result);
    }



}
