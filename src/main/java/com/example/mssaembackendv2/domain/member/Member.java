package com.example.mssaembackendv2.domain.member;

import com.example.mssaembackendv2.domain.mbti.MbtiEnum;
import com.example.mssaembackendv2.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String nickName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private MbtiEnum mbti;

    private boolean status = true;

    @NotNull
    private String refreshToken;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String caseSensitivity; //대소문자 구분

    private String profileImageUrl;

    private String introduction;

    @ColumnDefault("0")
    private Integer report; //신고수

    private String badgeName;

    private boolean defaultProfile = true;

    public void changeRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getDetailMbti() {
        char[] charArray = getMbti().toString().toCharArray();
        String caseSensitivity = getCaseSensitivity();

        for (int i = 0; i < caseSensitivity.length(); i++) {
            if (caseSensitivity.charAt(i) == '0') {
                charArray[i] = Character.toLowerCase(charArray[i]);
            }
        }
        return String.valueOf(charArray);
    }

    public Member(String email, String nickName, MbtiEnum mbti, String caseSensitivity) {
        this.email = email;
        this.nickName = nickName;
        this.mbti = mbti;
        this.caseSensitivity = caseSensitivity;
        this.profileImageUrl = mbti.getProfileUrl();
        this.refreshToken = "";
        this.report = 0;
        this.role = Role.ROLE_MEMBER;
    }

    public void modifyMember(String nickName, String introduction, MbtiEnum mbti,
                             String caseSensitivity, String badgeName) {
        // MBTI가 null이 아닐 때만 업데이트
        if (mbti != null) {
            this.mbti = mbti;
            // MBTI 변경 시 프로필 사진이 디폴트라면 프로필 사진도 같이 변경
            if (this.defaultProfile) {
                this.profileImageUrl = mbti.getProfileUrl();
            }
        }
        // 각 값이 null이 아닐 때만 업데이트
        if (nickName != null) {
            this.nickName = nickName;
        }
        if (introduction != null) {
            this.introduction = introduction;
        }
        if (caseSensitivity != null) {
            this.caseSensitivity = caseSensitivity;
        }
        if (badgeName != null) {
            this.badgeName = badgeName;
        }
    }

    public void increaseReport() {
        this.report++;
    }

    public void updateStatus() {
        this.status = false;
    }

    public void changeProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
        this.defaultProfile = false;
    }

    public void deleteProfile() {
        this.profileImageUrl = this.mbti.getProfileUrl();
        this.defaultProfile = true;
    }

    public void updateBadge(String badgeName) {
        this.badgeName = badgeName;
    }

}
