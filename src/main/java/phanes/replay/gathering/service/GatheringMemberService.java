package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import phanes.replay.exception.GatheringNotFoundException;
import phanes.replay.exception.UserNotFoundException;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.Gathering_Member;
import phanes.replay.gathering.domain.ParticipatingGatheringView;
import phanes.replay.gathering.domain.Role;
import phanes.replay.gathering.dto.response.GatheringMemberRs;
import phanes.replay.gathering.mapper.GatheringMemberMapper;
import phanes.replay.gathering.repository.GatheringRepository;
import phanes.replay.gathering.repository.Gathering_MemberRepository;
import phanes.replay.gathering.repository.ParticipatingGatheringViewRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GatheringMemberService {

    private final Gathering_MemberRepository gatheringMemberRepository;
    private final ParticipatingGatheringViewRepository participatingGatheringViewRepository;
    private final GatheringMemberMapper gatheringMemberMapper;
    private final UserRepository userRepository;
    private final GatheringRepository gatheringRepository;

    public Long getTotalGatheringCount(Long userId) {
        return gatheringMemberRepository.countByUserIdAndRoleEquals(userId, Role.HOST);
    }

    public Long getTotalMakeGatheringCount(Long userId, Role role) {
        return gatheringMemberRepository.countByUserIdAndRoleEquals(userId, role);
    }

    public List<ParticipatingGatheringView> getParticipatingGatheringView(Long userId, Pageable pageable) {
        return participatingGatheringViewRepository.findAllByUserId(userId, pageable);
    }

    public List<Gathering_Member> getMemberList(Set<Long> gatheringIdList) {
        return gatheringMemberRepository.findAllByMember(gatheringIdList);
    }

    public List<GatheringMemberRs> getMemberList(Long gatheringId) {
        return gatheringMemberRepository.findAllByGatheringId(gatheringId).stream().map(gatheringMemberMapper::toGatheringRs).toList();
    }

    public void addMember(Long userId, Long gatheringId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(() -> new GatheringNotFoundException("gathering not found"));
        Gathering_Member member = Gathering_Member.builder()
                .user(user)
                .gathering(gathering)
                .role(Role.MEMBER)
                .build();
        gatheringMemberRepository.save(member);
    }

    public void deleteMember(Long userId, Long gatheringId) {
        Gathering_Member member = gatheringMemberRepository.findByUserIdAndGatheringId(userId, gatheringId);
        gatheringMemberRepository.delete(member);
    }
}
