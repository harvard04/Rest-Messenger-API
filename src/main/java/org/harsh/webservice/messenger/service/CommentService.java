package org.harsh.webservice.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.harsh.webservice.messenger.database.DatabaseClass;
import org.harsh.webservice.messenger.model.Comment;
import org.harsh.webservice.messenger.model.ErrorMessage;
import org.harsh.webservice.messenger.model.Message;

public class CommentService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	
	public List<Comment> getAllComments(long messageId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();		// This map contains comments for a specific messageId
		return new ArrayList<Comment>(comments.values());							// and returns all those comments in the form of ArrayList
	}
	
	
	/*public Comment getComment(long messageId, long commentId){						
		Map<Long, Comment> comments = messages.get(messageId).getComments();		// This map contains comments for a specific messageId
		return comments.get(commentId);												// and retrieve the specific comment based on its commentId
	}*/
	
	
	public Comment getComment(long messageId, long commentId){
		
		ErrorMessage errorMessage = new ErrorMessage("Not Found",404 ,"http://www.google.com");
		
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		
		Message message = messages.get(messageId);
		if(message == null)
			throw new WebApplicationException(response);							// WebApplicationException() predefined in jersey so no need 
																					// to map in ExceptionMapper or implement or define it.
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		
		Comment comment = comments.get(commentId);
		if(comment == null)
			throw new NotFoundException(response);									// this is subclass of WebApplicationException where status has already has been set to status.NOT_FOUND
			//throw new WebApplicationException(response);
		return comment;
	}
	
	
	public Comment addComment(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();		// This map contains comments for a specific messageId
		comment.setId(comments.size() + 1);											// First increment the commentId to 1 more than the earlier comment size
		comments.put(comment.getId(), comment);										// then put the assign the comment to that specific id
		return comment;
	}
	
	
	public Comment updateComment(long messageId, Comment comment){
		Map<Long, Comment> comments = messages.get(messageId).getComments();		
		if(comment.getId() < 0)
			return null;
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	
	public Comment deleteComment(long messageId, long commentId){
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId); 
	}
	
}