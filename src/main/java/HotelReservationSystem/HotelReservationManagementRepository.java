package HotelReservationSystem;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface HotelReservationManagementRepository extends PagingAndSortingRepository<HotelReservationManagement, Long>{

    HotelReservationManagement findByReservationId(Long reservationId);

}