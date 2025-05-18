package phanes.replay.gathering.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import phanes.replay.exception.GatheringNotFoundException;
import phanes.replay.exception.UserNotFoundException;
import phanes.replay.gathering.domain.Gathering;
import phanes.replay.gathering.domain.GatheringMember;
import phanes.replay.gathering.domain.enums.Role;
import phanes.replay.gathering.dto.response.GatheringMemberRs;
import phanes.replay.gathering.mapper.GatheringMemberMapper;
import phanes.replay.gathering.repository.GatheringMemberRepository;
import phanes.replay.gathering.repository.GatheringRepository;
import phanes.replay.user.domain.User;
import phanes.replay.user.persistence.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GatheringMemberService {

    private final GatheringMemberRepository gatheringMemberRepository;
    private final GatheringMemberMapper gatheringMemberMapper;
    private final UserRepository userRepository;
    private final GatheringRepository gatheringRepository;

    public List<GatheringMemberRs> getMemberList(Long gatheringId) {
        return gatheringMemberRepository.findAllByGatheringId(gatheringId).stream().map(gatheringMemberMapper::toGatheringRs).toList();
    }

    public void addMember(Long userId, Long gatheringId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(() -> new GatheringNotFoundException("gathering not found"));
        GatheringMember member = GatheringMember.builder()
                .user(user)
                .gathering(gathering)
                .role(Role.MEMBER)
                .build();
        gatheringMemberRepository.save(member);
    }

    public void deleteMember(Long userId, Long gatheringId) {
        GatheringMember member = gatheringMemberRepository.findByUserIdAndGatheringId(userId, gatheringId);
        gatheringMemberRepository.delete(member);
    }
}
