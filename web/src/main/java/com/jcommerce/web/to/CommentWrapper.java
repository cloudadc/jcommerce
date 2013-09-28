package com.jcommerce.web.to;

import java.text.SimpleDateFormat;

import com.jcommerce.core.model.Comment;
import com.jcommerce.core.model.ModelObject;

public class CommentWrapper extends BaseModelWrapper {

	private static final long serialVersionUID = 8443649239714698175L;
	Comment comment;
	@Override
	protected Object getWrapped() {
		return getComment();
	}
	public CommentWrapper(ModelObject comment) {
		super();
		this.comment = (Comment)comment;
	}
	
	public Comment getComment() {
		return comment;
	}
	
	public int getCommentType() {
		return getComment().getCommentType();
	}
	
//	public String getCmtName() {
//		String goodsId = getComment().getIdValue();
//		Goods goods = (Goods) manager.get(ModelNames.GOODS, goodsId);
//		return goods.getGoodsName();
//	}

	public String getFormatedAddTime() {
		Long addTime = getComment().getAddTime().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
		return formatter.format(addTime);
	}
	
	public String getCommentId() {
		return getComment().getId();
	}
	
	public String getContent() {
		return getComment().getContent();
	}
	
//	public String getReplyContent() {
//		String id = getComment().getPkId();
//		Criteria criteria = new Criteria();
//		criteria.addCondition(new Condition(IComment.PARENT_ID, Condition.EQUALS, id));
//		List reply = manager.getList(ModelNames.COMMENT, criteria);
//		if(reply.size() == 0) {
//			return null;
//		}
//		else {
//			Comment comment = (Comment) reply.get(0);
//			return comment.getContent();
//		}
//	}
}
