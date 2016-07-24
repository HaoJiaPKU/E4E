package bfs;

import java.util.Comparator;
import java.util.PriorityQueue;



public class CoursePriorityQueue extends PriorityQueue <CourseNode>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4527465882086368009L;

	//优先队列比较器
	public CoursePriorityQueue ()
	{  
		super(1, new Comparator <CourseNode> ()
		{
			public int compare(CourseNode e1, CourseNode e2)
			{
				if(e1.f_n < e2.f_n)
				{
					return -1;
				}
				else if(e1.f_n == e2.f_n)
				{
					return 0;
				}
				else
				{
					return 1;
				}
			}
		});
	}

	//判断优先队列中是否存在某元素
	public boolean contains(Object e)
	{
		Object [] queue = toArray();
		for(int i = 0; i < queue.length; i ++)
		{
			if(( (CourseNode) queue[i]).equals( (CourseNode) e))
			{
				return true;
			}
		}  
        return false;
    }
	
	//获得优先队列中的元素
	public CourseNode getCourseNode(String courseName)
	{
		Object [] queue = toArray();
		for(int i = 0; i < queue.length; i ++)
		{
			if(( (CourseNode) queue[i]).courseName.equals(courseName))
			{
				return (CourseNode) queue[i];
			}
		}  
        return null;
	}
}

