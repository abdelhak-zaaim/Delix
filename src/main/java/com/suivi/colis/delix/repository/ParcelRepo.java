/*
 * **
 *  * @project : DeliX
 *  * @created : 23/04/2024, 19:10
 *  * @modified : 23/04/2024, 19:10
 *  * @description : This file is part of the DeliX project.
 *  * @license : MIT License
 *  **
 */

package com.suivi.colis.delix.repository;

import com.suivi.colis.delix.entity.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ParcelRepo extends JpaRepository<Parcel, Long> {
    boolean existsByCodeBar(String agencyCode);
    List<Parcel> findAllBySenderCustomerId(Long customerId);
    Parcel findByCodeBar(String codeBar);
    List<Parcel> findAllByCreationDateBetween(Date startDate, Date endDate);
}
