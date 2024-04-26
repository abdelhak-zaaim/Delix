/*
 * **
 *  * @project : SuiviColis
 *  * @created : 26/04/2024, 01:52
 *  * @modified : 26/04/2024, 01:24
 *  * @description : This file is part of the SuiviColis project.
 *  * @license : MIT License
 * **
 */

/*
 * **
 *  * @project : SuiviColis
 *  * @created : 23/04/2024, 18:13
 *  * @modified : 23/04/2024, 18:13
 *  * @description : This file is part of the SuiviColis project.
 *  * @license : MIT License
 *  **
 */

package com.suivi.colis.suivicolis.models.entities;

import com.suivi.colis.suivicolis.models.ParcelLocation;
import com.suivi.colis.suivicolis.models.converters.ParcelLocationConverter;
import com.suivi.colis.suivicolis.utils.Helper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParcelTracking {

    @Id
    private Long id;

    // the parcel that this track belongs to
    @ManyToOne
    @JoinColumn(name = "idParcel", referencedColumnName = "id")
    private Parcel idParcel;

    // the status of the parcel as a string
    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ParcelTracking;

    @Convert(converter = ParcelLocationConverter.class)
    private ParcelLocation currentLocation;

    @PrePersist
    protected void onCreated() {
        this.ParcelTracking = Helper.getCurrentDateWithSpecifiedTimeZone();

    }

}
