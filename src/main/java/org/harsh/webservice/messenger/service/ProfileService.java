package org.harsh.webservice.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.harsh.webservice.messenger.database.DatabaseClass;
import org.harsh.webservice.messenger.model.Profile;

public class ProfileService {

	private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService(){
		profiles.put("HARSH", new Profile(1L, "HARSH", "HARSH", ""));
		profiles.put("PLUTO", new Profile(2L, "PLUTO", "PLUTO", ""));
		profiles.put("GOOFY", new Profile(3L, "GOOFY", "GOOFY", ""));
		profiles.put("DONALD", new Profile(4L, "DONALD", "DONALD", ""));
		profiles.put("MIKE", new Profile(5L, "MIKE", "MIKE", ""));
	}

	public List<Profile> getProfiles(){
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String ProfileName){
		return profiles.get(ProfileName);
	}
	
	public Profile addProfile(Profile profile){
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile){
		if(profile.getProfileName().isEmpty())
			return null;
		profiles.put(profile.getProfileName(), profile);
		return profile;
		
	}
	
	public Profile deleteProfile(String ProfileName){
		return profiles.remove(ProfileName);
	}
}
