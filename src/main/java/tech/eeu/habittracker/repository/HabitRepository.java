package tech.eeu.habittracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.eeu.habittracker.model.HabitModel;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends JpaRepository<HabitModel, Long> {

    Optional<HabitModel> findById(Long id);

    @Query(nativeQuery = true, value = """
            SELECT * FROM habit
            WHERE category = :category
            ORDER BY id""")
    List<HabitModel> findAllByCategory(@Param("category") String category);
}
