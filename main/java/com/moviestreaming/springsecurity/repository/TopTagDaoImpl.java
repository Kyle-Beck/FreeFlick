package com.moviestreaming.springsecurity.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.moviestreaming.springsecurity.dto.TopTag;
import com.moviestreaming.springsecurity.enums.TagType;
import com.moviestreaming.springsecurity.dto.MoviePreview;

@Repository
public class TopTagDaoImpl implements TopTagDao {

	@Autowired
	private EntityManager session;

	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public List<TopTag> findTopTags(String username, TagType tagType, int limit) {

		Query query = session
				.createNativeQuery("select `name` from\r\n" + "	(select movieID from history\r\n"
						+ "	where username = ? and liked = 1) liked\r\n" + "join\r\n"
						+ "	(select `name`, movieID from tags \r\n" + "	where type = ?) t\r\n"
						+ "on liked.movieID = t.movieID\r\n" + "group by `name`\r\n" + "order by count(*) desc\r\n"
						+ "limit ?;")
				.setParameter(1, username).setParameter(2, tagType.toString()).setParameter(3, limit);

		query.unwrap(NativeQuery.class)
				// TODO warning to remove with Hibernate 6 for setResultTransformer()
				.setResultTransformer(Transformers.aliasToBean(TopTag.class)).list();

		List<TopTag> result = query.getResultList();
		return result;
	}

}
