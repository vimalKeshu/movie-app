package com.movie.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtMapper {
	
	private String jti;
	private long exp;
	private long iat;
	private String iss;
	private String preferred_username;
	private String sub;	
	private String aud;
	private Roles realm_access ;
	
	@JsonIgnoreProperties
	private int nbf;
	@JsonIgnoreProperties
	private String typ;
	@JsonIgnoreProperties
	private String azp;
	@JsonIgnoreProperties
	private int auth_time;
	@JsonIgnoreProperties
	private String session_state;
	@JsonIgnoreProperties
	private String acr;
	@JsonIgnoreProperties
	private List<String> allowed_origins;
	@JsonIgnoreProperties
	private String resource_access;	
	
	public JwtMapper() {}
	
	public String getJti() {
		return jti;
	}
	public long getExp() {
		return exp;
	}
	public long getIat() {
		return iat;
	}
	public String getIss() {
		return iss;
	}
	public String getPreferred_username() {
		return preferred_username;
	}
	public String getSub() {
		return sub;
	}
	public void setAud(String aud) {
		this.aud = aud;
	}	
	public Roles getRealm_access() {
		return realm_access;
	}
	public void setJti(String jti) {
		this.jti = jti;
	}
	public void setExp(long exp) {
		this.exp = exp;
	}
	public void setIat(long iat) {
		this.iat = iat;
	}
	public void setIss(String iss) {
		this.iss = iss;
	}
	public void setPreferred_username(String preferred_username) {
		this.preferred_username = preferred_username;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public void setRealm_access(Roles realm_access) {
		this.realm_access = realm_access;
	}

	public String getAud() {
		return aud;
	}

	public int getNbf() {
		return nbf;
	}

	public String getTyp() {
		return typ;
	}

	public String getAzp() {
		return azp;
	}

	public int getAuth_time() {
		return auth_time;
	}

	public String getSession_state() {
		return session_state;
	}

	public String getAcr() {
		return acr;
	}

	@JsonProperty("allowed-origins")
	public List<String> getAllowed_origins() {
		return allowed_origins;
	}

	public String getResource_access() {
		return resource_access;
	}

	public void setNbf(int nbf) {
		this.nbf = nbf;
	}

	public void setTyp(String typ) {
		this.typ = typ;
	}

	public void setAzp(String azp) {
		this.azp = azp;
	}

	public void setAuth_time(int auth_time) {
		this.auth_time = auth_time;
	}

	public void setSession_state(String session_state) {
		this.session_state = session_state;
	}

	public void setAcr(String acr) {
		this.acr = acr;
	}

	public void setAllowed_origins(List<String> allowed_origins) {
		this.allowed_origins = allowed_origins;
	}

	public void setResource_access(String resource_access) {
		this.resource_access = resource_access;
	}
	
	
}
