package phanes.replay.gathering.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import phanes.replay.gathering.domain.Gathering;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {

//    @Query("SELECT g from Gathering g where g.capacity >= :limit")
//    List<Gathering> findByCapacity(@Param("limit") int limit);



    // JPQL 작성
    @Query("Select g From Gathering g where g.name = :name")
    List<Gathering> findGatherings(@Param("name") String name);

    List<Gathering> findByNameContaining(String keyword, Pageable pageable);


    // 키워드로 검색 (모임 이름 또는 방탈출 이름에 키워드가 포함된 경우)
    @Query("select g from Gathering g join g.theme r where g.name like %:keyword% OR r.name like %:roomKeyword%")
    List<Gathering> findByNameContainingOrRoomEscapeNameContaining(
            @Param("keyword")String keyword,
            @Param("roomKeyword")String roomKeyword,
            Pageable pageable);

    // 지역으로 검색
    @Query("select g from Gathering g join g.theme r WHERE r.address LIKE %:location%")
    List<Gathering> findByLocation(@Param("location")String location, Pageable pageable);

    // 날짜로 검색 (해당 날짜에 열리는 모임)
    @Query("select g from Gathering g where g.dateTime between :start AND :end")
    List<Gathering> findByDateTimeBetween(
            @Param("start")LocalDateTime start,
            @Param("end")LocalDateTime end,
            Pageable pageable);

    // 장르로 검색
    @Query("select g from Gathering g join g.theme r join r.genres gen where gen.name = :genre")
    List<Gathering> findByGenre(@Param("genre")String genre, Pageable pageable);

    // 다중필터
    @Query("SELECT g FROM Gathering g JOIN g.theme r LEFT JOIN r.genres gen " +
            "WHERE (:keyword IS NULL OR g.name LIKE %:keyword% OR r.name LIKE %:keyword%) " +
            "AND (:location IS NULL OR r.address LIKE %:location%) " +
            "AND (:date IS NULL OR DATE(g.dateTime) = DATE(:date)) " +
            "AND (:genre IS NULL OR gen.name = :genre)")
    Page<Gathering> findGatheringsWithFilters(
            @Param("keyword") String keyword,
            @Param("location") String location,
            @Param("date") LocalDateTime date,
            @Param("genre") String genre,
            Pageable pageable);
}
