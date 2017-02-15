package org.harsh.webservice.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.harsh.webservice.messenger.model.Comment;
import org.harsh.webservice.messenger.service.CommentService;

@Path("/")									// optional because the control has already been directed to this class from MessageResource
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

	private CommentService commentservice = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId){
		return commentservice.getAllComments(messageId);
	}
	
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId, Comment comment){
		return commentservice.addComment(messageId, comment);
	}

	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId, Comment comment){
		comment.setId(commentId);
		return commentservice.updateComment(messageId, comment);
	}
	

	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId){
		commentservice.deleteComment(messageId, commentId);
	}

	
	@GET
	@Path("/{commentId}")
	public Comment getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId){
		return commentservice.getComment(messageId, commentId);
	}
}


// Tester CODE

/*@GET
public String test(){
	return "inside comment resource";
}

@GET
@Path("/{commentId}")
public String Test2(@PathParam ("messageId") long messageId,
					@PathParam ("commentId") long commentId){
	return "inside comment:  " + commentId +  "  for message  " + messageId;
}*/