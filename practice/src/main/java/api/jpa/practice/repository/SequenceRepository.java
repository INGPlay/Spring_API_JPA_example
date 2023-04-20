package api.jpa.practice.repository;

import api.jpa.practice.entity.sequenceTables.ContainerSequence;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class SequenceRepository {
    private final EntityManager em;

    public Optional<Long> getContainerIndexByUserId(Long userId){
        return Optional.ofNullable(
                em.find(ContainerSequence.class, userId).getSequenceIndex()
        );
    }

    public Long plusOneContainerIndexByUserId(Long userId){
        ContainerSequence containerSequence = em.find(ContainerSequence.class, userId);

        Long resultIndex = containerSequence.getSequenceIndex() + 1;
        containerSequence.setSequenceIndex(
                resultIndex);
        log.info("ContainerIndex : {}", resultIndex);
        return resultIndex;
    }
}
