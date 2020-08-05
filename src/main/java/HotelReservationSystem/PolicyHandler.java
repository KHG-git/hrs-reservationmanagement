package HotelReservationSystem;

import HotelReservationSystem.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    HotelReservationManagementRepository hotelReservationManagementRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentApproved_Reservationreceive(@Payload PaymentApproved paymentApproved){

        if(paymentApproved.isMe()){
            System.out.println("##### listener Reservationreceive : " + paymentApproved.toJson());

            HotelReservationManagement reservationManagement = new HotelReservationManagement();

            reservationManagement.setReservationId(paymentApproved.getReservationId());
            reservationManagement.setStatus("ReservationConfirmed");
            reservationManagement.setCustomerName(paymentApproved.getCustomerName());

            hotelReservationManagementRepository.save(reservationManagement);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCanceled_Reservationreceive(@Payload PaymentCanceled paymentCanceled){

        if(paymentCanceled.isMe()){
            System.out.println("##### listener Reservationreceive : " + paymentCanceled.toJson());

            HotelReservationManagement reservationManagement = hotelReservationManagementRepository.findByReservationId(paymentCanceled.getReservationId());

            if(reservationManagement != null){
                reservationManagement.setReservationId(paymentCanceled.getReservationId());
                reservationManagement.setStatus("ReservationCanceled");
                reservationManagement.setCustomerName(paymentCanceled.getCustomerName());
                hotelReservationManagementRepository.save(reservationManagement);
            }

        }
    }

}
