package HotelReservationSystem;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="HotelReservationManagement_table")
public class HotelReservationManagement {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long reservationId;
    private String status;
    private String customerName;

    @PostPersist
    public void onPostPersist(){
        ReservationConfirmed reservationConfirmed = new ReservationConfirmed();
        BeanUtils.copyProperties(this, reservationConfirmed);
        reservationConfirmed.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.


        System.out.println("##### pointsave call start ###################### : ");

//        // mappings goes here
        HotelReservationSystem.external.Point point = new HotelReservationSystem.external.Point();
        point.setPointAmount(10);
        point.setReservationId(reservationConfirmed.getReservationId());
        point.setCustomerName(reservationConfirmed.getCustomerName());
        ReservationmanagementApplication.applicationContext.getBean(HotelReservationSystem.external.PointService.class)
            .pointsave(point);


        System.out.println("##### pointsave call end ###################### : ");

    }

//    @PostPersist
//    public void onPostPersistPoint(){
//
//        //Following code causes dependency to external APIs
//        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.
//
//        ReservationConfirmed reservationConfirmed = new ReservationConfirmed();
//        BeanUtils.copyProperties(this, reservationConfirmed);
//
//        HotelReservationSystem.external.Point point = new HotelReservationSystem.external.Point();
//        // mappings goes here
//        point.setPointAmount(10);
//        point.setCustomerName(reservationConfirmed.getCustomerName());
//        ReservationmanagementApplication.applicationContext.getBean(HotelReservationSystem.external.PointService.class)
//                .pointsave(point);
//
//    }

    @PostUpdate
    public void onPostUpdate(){
        ReservationConfirmedCanceled reservationConfirmedCanceled = new ReservationConfirmedCanceled();
        BeanUtils.copyProperties(this, reservationConfirmedCanceled);
        reservationConfirmedCanceled.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }




}
