package hu.tundik.progenv.model;

import java.sql.Date;

public class Rental
{
    private int id;
    private int customerId;
    private int dvdId;
    private Date rentalDate;
    private Date returnDate;

    public Rental() {}

    public Rental(int id, int customerId, int dvdId, Date rentalDate, Date returnDate) {
        this.id = id;
        this.customerId = customerId;
        this.dvdId = dvdId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getDvdId() { return dvdId; }
    public void setDvdId(int dvdId) { this.dvdId = dvdId; }

    public Date getRentalDate() { return rentalDate; }
    public void setRentalDate(Date rentalDate) { this.rentalDate = rentalDate; }

    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

    @Override
    public String toString()
    {
        return "Rental[id=" + id + ", customerId=" + customerId + ", dvdId=" + dvdId + "]";
    }
}
