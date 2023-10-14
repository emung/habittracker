package tech.eeu.habittracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.eeu.habittracker.model.HabitModel;

import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<HabitModel, Long> {

    Optional<HabitModel> findById(Long id);
}
