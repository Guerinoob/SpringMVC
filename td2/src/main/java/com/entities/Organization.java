package com.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class Organization {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private String domain;
	private String aliases;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="organization")
	@OrderColumn
	private List<Groupe> groupes;
	
	private String organizationSettings;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="organization")
	@OrderColumn
	private List<User> users;
	
	public Organization() {
		groupes = new ArrayList<Groupe>();
		users = new ArrayList<User>();
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getAliases() {
		return aliases;
	}
	public void setAliases(String aliases) {
		this.aliases = aliases;
	}
	public List<Groupe> getGroupes() {
		return groupes;
	}
	public void addGroupe(Groupe groupe) {
		this.groupes.add(groupe);
		groupe.setOrganization(this);
	}
	public String getOrganizationSettings() {
		return organizationSettings;
	}
	public void setOrganizationSettings(String organizationSettings) {
		this.organizationSettings = organizationSettings;
	}
	public List<User> getUsers() {
		return users;
	}
	public void addUser(User user) {
		this.users.add(user);
		user.setOrganization(this);
	}
	
	@Override
	public String toString() {
		return name;
	}

}
