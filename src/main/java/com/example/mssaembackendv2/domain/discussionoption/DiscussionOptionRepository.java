package com.example.mssaembackendv2.domain.discussionoption;

import com.example.mssaembackendv2.domain.discussion.Discussion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscussionOptionRepository extends JpaRepository<DiscussionOption, Long> {

    List<DiscussionOption> findDiscussionOptionByDiscussion(Discussion discussion);

    void deleteAllByDiscussion(Discussion discussion);
}
