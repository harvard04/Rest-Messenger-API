package org.harsh.webservice.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.harsh.webservice.messenger.database.DatabaseClass;
import org.harsh.webservice.messenger.exception.DataNotFoundException;
import org.harsh.webservice.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	
	public MessageService(){
		messages.put(1L, new Message(1L, "Hello World" , new Date() , "Harsh"));
		messages.put(2L, new Message(2L, "Hello Folks" , new Date() , "Ravi"));
		messages.put(3L, new Message(3L, "Hello India" , new Date() , "Akshat"));
		messages.put(4L, new Message(4L, "Hello USA" , new Date() , "Rags"));
		messages.put(5L, new Message(5L, "Hello UK" , new Date() , "Abdul"));
	}
	
	
	public List<Message> getallmessages(){

		return new ArrayList<>(messages.values());

		/*Message msg1 = new Message(1L, "Hello World" ,null, "Harsh");
		Message msg2 = new Message(1L, "Hello Folks" ,null, "Ravi");
		Message msg3 = new Message(1L, "Hello India" ,null, "Akshat");
		Message msg4 = new Message(1L, "Hello USA" ,null, "Rags");
		Message msg5 = new Message(1L, "Hello UK" ,null, "Abdul");
		List<Message> list = new ArrayList<>();
		list.add(msg1);
		list.add(msg2);
		list.add(msg3);
		list.add(msg4);
		list.add(msg5);
		return list;*/
	}


	public List<Message> getAllMessagesForYear(int year){
		List<Message> messageForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values())
		{
			cal.setTime(message.getDate());
			if(cal.get(Calendar.YEAR)== year)
			{
				messageForYear.add(message);
			}
		}
		return messageForYear;
 	}
	
	
	public List<Message> getAllMessagesPaginated(int start, int size){
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(start + size > list.size())
			return new ArrayList<Message>();
		return list.subList(start, start+size);
	}
	
	
	public Message getMessage(Long id){
		Message message = messages.get(id);
		if(message == null)
			throw new DataNotFoundException("Message with id " + id + " not found");
		else
			return message;
	}


	public Message addMessage(Message message){
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	
	public Message deleteMessage(long id){
		return messages.remove(id);
	}

	
	public Message updateMessage(Message message){
		if(message.getId() < 0)
			return null;
		else{
			messages.put(message.getId(), message);
			return message;
		}

		/*public Message updateMessage(long id, Message message){
			return messages.replace(id, message);
		}
		 */
	}
}
