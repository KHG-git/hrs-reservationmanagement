
package HotelReservationSystem.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name="point", url="${external.url.point}")
public interface PointService {

    @RequestMapping(method= RequestMethod.POST, path="/points")
    public void pointsave(@RequestBody Point point);

}