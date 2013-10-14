 
 想必这个案例，只要是做过应用系统的小伙伴们，都应该遇到过吧？无外乎数据库里就两张表：
 	product 与 log，用两条 SQL 语句应该可以解决问题：
 	
1	update product set price = ? where id = ?
2	insert into log (created, description) values (?, ?)

But！要确保这两条 SQL 语句必须在同一个事务里进行提交，否则有可能 update 提交了，但 
	insert 却没有提交。如果这样的事情真的发生了，我们肯定会被用户指着鼻子狂骂：为什么产品价格改了，却看不到什么时候改的呢 