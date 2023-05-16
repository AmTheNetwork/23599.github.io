package com.aircondition.refrigerationsys.Repository;

import com.aircondition.refrigerationsys.Model.AirCon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirRepository extends JpaRepository<AirCon, Long> {


}
