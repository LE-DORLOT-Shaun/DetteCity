package entities;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;


public class HistoriqueCapteurPolluant {
	
	private Long id;
	private Timestamp start_date;
	private String val_co2 ;
	private String val_no2;
	private String val_pf;
	private String val_tmp;
	private Long fk_id_capteur;
	
	public HistoriqueCapteurPolluant() {
		
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Timestamp getStart_date() {
		return start_date;
	}
	public void setStart_date(Timestamp start_date) {
		this.start_date = start_date;
	}
	public String getVal_co2() {
		return val_co2;
	}
	public void setVal_co2(String val_co2) {
		this.val_co2 = val_co2;
	}
	public String getVal_no2() {
		return val_no2;
	}
	public void setVal_no2(String val_no2) {
		this.val_no2 = val_no2;
	}
	public String getVal_pf() {
		return val_pf;
	}
	public void setVal_pf(String val_pf) {
		this.val_pf = val_pf;
	}
	public String getVal_tmp() {
		return val_tmp;
	}
	public void setVal_tmp(String val_tmp) {
		this.val_tmp = val_tmp;
	}
	public Long getFk_id_capteur() {
		return fk_id_capteur;
	}
	public void setFk_id_capteur(Long fk_id_capteur) {
		this.fk_id_capteur = fk_id_capteur;
	}
	
	public String getDate_signal_formatted() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(start_date);
	}
	@Override
	public String toString() {
		return "HistoriqueCapteurPolluant [id=" + id + ", start_date=" + start_date + ", val_co2=" + val_co2
				+ ", val_no2=" + val_no2 + ", val_pf=" + val_pf + ", val_tmp=" + val_tmp + ", fk_id_capteur="
				+ fk_id_capteur + "]";
	}
	


}