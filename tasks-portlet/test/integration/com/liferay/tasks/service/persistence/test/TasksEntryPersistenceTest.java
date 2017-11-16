/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.tasks.service.persistence.test;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import com.liferay.tasks.exception.NoSuchTasksEntryException;
import com.liferay.tasks.model.TasksEntry;
import com.liferay.tasks.service.TasksEntryLocalServiceUtil;
import com.liferay.tasks.service.persistence.TasksEntryPersistence;
import com.liferay.tasks.service.persistence.TasksEntryUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class TasksEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = TasksEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<TasksEntry> iterator = _tasksEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TasksEntry tasksEntry = _persistence.create(pk);

		Assert.assertNotNull(tasksEntry);

		Assert.assertEquals(tasksEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		TasksEntry newTasksEntry = addTasksEntry();

		_persistence.remove(newTasksEntry);

		TasksEntry existingTasksEntry = _persistence.fetchByPrimaryKey(newTasksEntry.getPrimaryKey());

		Assert.assertNull(existingTasksEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addTasksEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TasksEntry newTasksEntry = _persistence.create(pk);

		newTasksEntry.setGroupId(RandomTestUtil.nextLong());

		newTasksEntry.setCompanyId(RandomTestUtil.nextLong());

		newTasksEntry.setUserId(RandomTestUtil.nextLong());

		newTasksEntry.setUserName(RandomTestUtil.randomString());

		newTasksEntry.setCreateDate(RandomTestUtil.nextDate());

		newTasksEntry.setModifiedDate(RandomTestUtil.nextDate());

		newTasksEntry.setTitle(RandomTestUtil.randomString());

		newTasksEntry.setPriority(RandomTestUtil.nextInt());

		newTasksEntry.setAssigneeUserId(RandomTestUtil.nextLong());

		newTasksEntry.setResolverUserId(RandomTestUtil.nextLong());

		newTasksEntry.setDueDate(RandomTestUtil.nextDate());

		newTasksEntry.setFinishDate(RandomTestUtil.nextDate());

		newTasksEntry.setStatus(RandomTestUtil.nextInt());

		_tasksEntries.add(_persistence.update(newTasksEntry));

		TasksEntry existingTasksEntry = _persistence.findByPrimaryKey(newTasksEntry.getPrimaryKey());

		Assert.assertEquals(existingTasksEntry.getTasksEntryId(),
			newTasksEntry.getTasksEntryId());
		Assert.assertEquals(existingTasksEntry.getGroupId(),
			newTasksEntry.getGroupId());
		Assert.assertEquals(existingTasksEntry.getCompanyId(),
			newTasksEntry.getCompanyId());
		Assert.assertEquals(existingTasksEntry.getUserId(),
			newTasksEntry.getUserId());
		Assert.assertEquals(existingTasksEntry.getUserName(),
			newTasksEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingTasksEntry.getCreateDate()),
			Time.getShortTimestamp(newTasksEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingTasksEntry.getModifiedDate()),
			Time.getShortTimestamp(newTasksEntry.getModifiedDate()));
		Assert.assertEquals(existingTasksEntry.getTitle(),
			newTasksEntry.getTitle());
		Assert.assertEquals(existingTasksEntry.getPriority(),
			newTasksEntry.getPriority());
		Assert.assertEquals(existingTasksEntry.getAssigneeUserId(),
			newTasksEntry.getAssigneeUserId());
		Assert.assertEquals(existingTasksEntry.getResolverUserId(),
			newTasksEntry.getResolverUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingTasksEntry.getDueDate()),
			Time.getShortTimestamp(newTasksEntry.getDueDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingTasksEntry.getFinishDate()),
			Time.getShortTimestamp(newTasksEntry.getFinishDate()));
		Assert.assertEquals(existingTasksEntry.getStatus(),
			newTasksEntry.getStatus());
	}

	@Test
	public void testCountByGroupId() throws Exception {
		_persistence.countByGroupId(RandomTestUtil.nextLong());

		_persistence.countByGroupId(0L);
	}

	@Test
	public void testCountByUserId() throws Exception {
		_persistence.countByUserId(RandomTestUtil.nextLong());

		_persistence.countByUserId(0L);
	}

	@Test
	public void testCountByAssigneeUserId() throws Exception {
		_persistence.countByAssigneeUserId(RandomTestUtil.nextLong());

		_persistence.countByAssigneeUserId(0L);
	}

	@Test
	public void testCountByResolverUserId() throws Exception {
		_persistence.countByResolverUserId(RandomTestUtil.nextLong());

		_persistence.countByResolverUserId(0L);
	}

	@Test
	public void testCountByG_U() throws Exception {
		_persistence.countByG_U(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_U(0L, 0L);
	}

	@Test
	public void testCountByG_A() throws Exception {
		_persistence.countByG_A(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_A(0L, 0L);
	}

	@Test
	public void testCountByG_R() throws Exception {
		_persistence.countByG_R(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByG_R(0L, 0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		TasksEntry newTasksEntry = addTasksEntry();

		TasksEntry existingTasksEntry = _persistence.findByPrimaryKey(newTasksEntry.getPrimaryKey());

		Assert.assertEquals(existingTasksEntry, newTasksEntry);
	}

	@Test(expected = NoSuchTasksEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	@Test
	public void testFilterFindByGroupId() throws Exception {
		_persistence.filterFindByGroupId(0, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, getOrderByComparator());
	}

	protected OrderByComparator<TasksEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("TMS_TasksEntry",
			"tasksEntryId", true, "groupId", true, "companyId", true, "userId",
			true, "userName", true, "createDate", true, "modifiedDate", true,
			"title", true, "priority", true, "assigneeUserId", true,
			"resolverUserId", true, "dueDate", true, "finishDate", true,
			"status", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		TasksEntry newTasksEntry = addTasksEntry();

		TasksEntry existingTasksEntry = _persistence.fetchByPrimaryKey(newTasksEntry.getPrimaryKey());

		Assert.assertEquals(existingTasksEntry, newTasksEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TasksEntry missingTasksEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingTasksEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		TasksEntry newTasksEntry1 = addTasksEntry();
		TasksEntry newTasksEntry2 = addTasksEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTasksEntry1.getPrimaryKey());
		primaryKeys.add(newTasksEntry2.getPrimaryKey());

		Map<Serializable, TasksEntry> tasksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, tasksEntries.size());
		Assert.assertEquals(newTasksEntry1,
			tasksEntries.get(newTasksEntry1.getPrimaryKey()));
		Assert.assertEquals(newTasksEntry2,
			tasksEntries.get(newTasksEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, TasksEntry> tasksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(tasksEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		TasksEntry newTasksEntry = addTasksEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTasksEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, TasksEntry> tasksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, tasksEntries.size());
		Assert.assertEquals(newTasksEntry,
			tasksEntries.get(newTasksEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, TasksEntry> tasksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(tasksEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		TasksEntry newTasksEntry = addTasksEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newTasksEntry.getPrimaryKey());

		Map<Serializable, TasksEntry> tasksEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, tasksEntries.size());
		Assert.assertEquals(newTasksEntry,
			tasksEntries.get(newTasksEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = TasksEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<TasksEntry>() {
				@Override
				public void performAction(TasksEntry tasksEntry) {
					Assert.assertNotNull(tasksEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		TasksEntry newTasksEntry = addTasksEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TasksEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tasksEntryId",
				newTasksEntry.getTasksEntryId()));

		List<TasksEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		TasksEntry existingTasksEntry = result.get(0);

		Assert.assertEquals(existingTasksEntry, newTasksEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TasksEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tasksEntryId",
				RandomTestUtil.nextLong()));

		List<TasksEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		TasksEntry newTasksEntry = addTasksEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TasksEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"tasksEntryId"));

		Object newTasksEntryId = newTasksEntry.getTasksEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("tasksEntryId",
				new Object[] { newTasksEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingTasksEntryId = result.get(0);

		Assert.assertEquals(existingTasksEntryId, newTasksEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(TasksEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"tasksEntryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("tasksEntryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected TasksEntry addTasksEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		TasksEntry tasksEntry = _persistence.create(pk);

		tasksEntry.setGroupId(RandomTestUtil.nextLong());

		tasksEntry.setCompanyId(RandomTestUtil.nextLong());

		tasksEntry.setUserId(RandomTestUtil.nextLong());

		tasksEntry.setUserName(RandomTestUtil.randomString());

		tasksEntry.setCreateDate(RandomTestUtil.nextDate());

		tasksEntry.setModifiedDate(RandomTestUtil.nextDate());

		tasksEntry.setTitle(RandomTestUtil.randomString());

		tasksEntry.setPriority(RandomTestUtil.nextInt());

		tasksEntry.setAssigneeUserId(RandomTestUtil.nextLong());

		tasksEntry.setResolverUserId(RandomTestUtil.nextLong());

		tasksEntry.setDueDate(RandomTestUtil.nextDate());

		tasksEntry.setFinishDate(RandomTestUtil.nextDate());

		tasksEntry.setStatus(RandomTestUtil.nextInt());

		_tasksEntries.add(_persistence.update(tasksEntry));

		return tasksEntry;
	}

	private List<TasksEntry> _tasksEntries = new ArrayList<TasksEntry>();
	private TasksEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}