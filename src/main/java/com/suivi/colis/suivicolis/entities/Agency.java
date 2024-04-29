
/*
 * **
 *  * @project : SuiviColis
 *  * @created : 23/04/2024, 18:24
 *  * @modified : 23/04/2024, 18:24
 *  * @description : This file is part of the SuiviColis project.
 *  * @license : MIT License
 *  **
 */

package com.suivi.colis.suivicolis.entities;

import com.suivi.colis.suivicolis.models.MapsLocationPoint;
import com.suivi.colis.suivicolis.utils.helpers.DateUtils;
import com.suivi.colis.suivicolis.validations.location.ValidMapsLocationPoint;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity

public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String agencyCode;
    @Column(nullable = false)
    private String agencyName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address agencyAddress;
    private Date agencyEstablishedDate;
    @ManyToOne
    private AdminEmployee createdBy;
    private String agencyContactNumber;
    private String agencyEmail;
    @OneToMany(mappedBy = "associatedAgency")
    @ToString.Exclude
    private Set<AgencyEmployee> agencyEmployees;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateDate;

    @ValidMapsLocationPoint
    private MapsLocationPoint locationPoint;

    public Agency(String agencyCode, String agencyName, Address agencyAddress, AdminEmployee createdBy) {
        this.agencyCode = agencyCode;
        this.agencyName = agencyName;
        this.agencyAddress = agencyAddress;
        this.createdBy = createdBy;
    }

    @PrePersist
    protected void onCreated() {
        Date date = DateUtils.getCurrentDateWithSpecifiedTimeZone();
        this.creationDate = date;
        this.lastUpdateDate = date;
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastUpdateDate = DateUtils.getCurrentDateWithSpecifiedTimeZone();
    }


}
