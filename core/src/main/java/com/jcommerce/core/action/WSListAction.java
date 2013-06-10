/**
 * 
 */
package com.jcommerce.core.action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.jcommerce.core.model.ModelObject;
import com.jcommerce.core.service.Criteria;
import com.jcommerce.core.service.Manager;

/**
 * @author david yang
 *
 */
public class WSListAction extends WSAction {

	/**
	 * @param ctx
	 * @param config
	 */
	public WSListAction(ApplicationContext ctx, BeanConfig config) {
		super(ctx, config);
		// TODO Auto-generated constructor stub
	}
	
	public List<ModelObject> getListObj(String modelName, String id, int count, int begin, Criteria criteria) {
		Manager manager = getManager(modelName);
		List<ModelObject> list = new ArrayList<ModelObject>();
		if (id.trim().equals("")) {
			System.out.println("getList(" + modelName + ")");
			String methodName = config.getListMethod(modelName);
			try {
				Class[] params = new Class[1];
				params[0] = criteria.getClass();
				Method method = manager.getClass().getMethod(methodName, params);
				if (method == null) {
					System.out.println("Method not found: " + methodName);
				}
				Object[] objs = new Object[1];
				objs[0] = criteria;
				System.out.println("invoke manager begin");
				List ret = (List) method.invoke(manager, objs);
				System.out.println("invoke manager end " + ret.size());
				int counter = count;
				int pointer = 0;
				for (Iterator it = ret.iterator(); it.hasNext();) {
					pointer = pointer + 1;
					ModelObject model = (ModelObject) it.next();
					if (count == 0) {
						list.add(model);
					} else {	
						if (pointer >= begin) {
							list.add(model);
							counter--;
						}
						if (counter == 0) {
							break;
						}
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("getBean(" + modelName + ")");
			String methodName = config.getGetMethod(modelName);
			try {
				Method method = manager.getClass().getMethod(methodName, new Class[] { String.class });
				if (method == null) {
					System.out.println("Method not found: " + methodName);
				}
				ModelObject model = (ModelObject) method.invoke(manager, id);
				list.add(model);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public List<ModelObject> getListObj(String modelName, String id, int count, int begin) {
		Manager manager = getManager(modelName);
		List<ModelObject> list = new ArrayList<ModelObject>();
		if (id.trim().equals("")) {
			System.out.println("getList(" + modelName + ")");
			String methodName = config.getListMethod(modelName);
			try {
				Method method = manager.getClass().getMethod(methodName, new Class[0]);
				if (method == null) {
					System.out.println("Method not found: " + methodName);
				}
				System.out.println("invoke manager begin");
				List ret = (List) method.invoke(manager);
				System.out.println("invoke manager end " + ret.size());
				int counter = count;
				for (Iterator it = ret.iterator(); it.hasNext();) {
					ModelObject model = (ModelObject) it.next();
					if (count == 0) {
						list.add(model);
					} else {
						String currentId = getId(model);
						if (Integer.parseInt(currentId) > begin) {
							list.add(model);
							counter--;
						}
						if (counter == 0) {
							break;
						}
					}
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		} else {
			System.out.println("getBean(" + modelName + ")");
			String methodName = config.getGetMethod(modelName);
			try {
				Method method = manager.getClass().getMethod(methodName, new Class[] { String.class });
				if (method == null) {
					System.out.println("Method not found: " + methodName);
				}
				ModelObject model = (ModelObject) method.invoke(manager, id);
				list.add(model);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	public List<ModelObject> getListObj(String modelName, Criteria criteria) {
		Manager manager = getManager(modelName);
		List<ModelObject> list = new ArrayList<ModelObject>();
		if (criteria != null) {
			System.out.println("getList(" + modelName + ") with criteria");
			String methodName = config.getListMethod(modelName);
			try {
				Class[] params = new Class[1];
				params[0] = criteria.getClass();
				Method method = manager.getClass().getMethod(methodName, params);
				if (method == null) {
					System.out.println("Method not found: " + methodName);
				}
				Object[] objs = new Object[1];
				objs[0] = criteria;
				System.out.println("invoke manager begin");
				List ret = (List) method.invoke(manager, objs);
				System.out.println("invoke manager end " + ret.size());
				for (Iterator it = ret.iterator(); it.hasNext();) {
					ModelObject model = (ModelObject) it.next();
					list.add(model);
				}
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}

		}
		return list;
	}
	
	public int getTotalCount(String modelName) {
		int totalNumber = 0;

		Manager manager = getManager(modelName);
		String methodName = config.getListMethod(modelName);
		try {
			Method method = manager.getClass().getMethod(methodName, new Class[0]);
			if (method == null) {
				System.out.println("Method not found: " + methodName);
			}
			List ret = (List) method.invoke(manager);
			if (ret != null) {
				totalNumber = ret.size();
			}

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return totalNumber;
	}

	/**
	 * no tested.
	 * @param modelName
	 * @param criteria
	 * @return
	 */
	public int getTotalCount(String modelName, Criteria criteria) {
		int totalNumber = 0;

		Manager manager = getManager(modelName);
		String methodName = config.getCountMethod(modelName);
		try {
			Class[] params = new Class[1];
			params[0] = criteria.getClass();
			Method method = manager.getClass().getMethod(methodName, params);
			if (method == null) {
				System.out.println("Method not found: " + methodName);
			}
			Object[] objs = new Object[1];
			objs[0] = criteria;
			System.out.println("invoke manager begin");
			totalNumber = Integer.parseInt(method.invoke(manager, objs).toString());
			System.out.println("invoke manager end ");
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return totalNumber;
	}

}
