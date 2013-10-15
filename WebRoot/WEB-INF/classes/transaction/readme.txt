basic conceptions:
The properties about Transaction:
	ACID
1 Atomicity	原子性
2 Consistency 一致性
3 Isolation 隔离性
4 Durability 持久性

5 Transaction Isolation Level 事务隔离级别
	
    READ_UNCOMMITTED
    READ_COMMITTED
    REPEATABLE_READ
    SERIALIZABLE
	
 千万不要去翻译，那只是一个代号而已。从上往下，级别越来越高，并发性越来越差，安全性越来越高，反之则反。 		
    
原子性是基础，隔离性是手段，持久性是目的，真正的老大就是一致性

其实最难理解的反倒不是一致性，而是隔离性。因为它是保证一致性的重要手段，是工具，使用它不能有半点差池
否则后果自负！怪不得数据库行业专家们都要来研究所谓的事务隔离级别了。其实，定义这四个级别就是为了解决
数据在高并发下所产生的问题，那又有哪些问题呢
	
    Dirty Read（脏读）
    Unrepeatable Read（不可重复读）
    Phantom Read（幻读）
    
 归纳一下，以上提到了事务并发所引起的跟读取数据有关的问题，各用一句话来描述一下：

    脏读：事务 A 读取了事务 B 未提交的数据，并在这个基础上又做了其他操作。
    不可重复读：事务 A 读取了事务 B 已提交的更改数据。
    幻读：事务 A 读取了事务 B 已提交的新增数据。

第一条是坚决抵制的，后两条在大多数情况下可不作考虑。


我们知道 JDBC 只是连接 Java 程序与数据库的桥梁而已，那么数据库又是怎样隔离事务的呢？其实它就是“锁”这个东西。
当插入数据时，就锁定表，这叫“锁表”；当更新数据时，就锁定行，这叫“锁行”。当然这个已经超出了我们今天讨论的范围，
所以还是留点空间给我们的 DBA 同学吧，免得他没啥好写的了。

不妨看看 Spring 的解决方案吧，其实它是对 JDBC 的一个补充或扩展。它提供了一个非常重要的功能，就是：事务传播行为
【Transaction Propagation Behavior】

    
1    PROPAGATION_REQUIRED
2    RROPAGATION_REQUIRES_NEW
3    PROPAGATION_NESTED
4    PROPAGATION_SUPPORTS
5    PROPAGATION_NOT_SUPPORTED
6    PROPAGATION_NEVER
7    PROPAGATION_MANDATORY
    
 假设事务从方法 A 传播到方法 B，您需要面对方法 B，问自己一个问题：

方法 A 有事务吗？

1    如果没有，就新建一个事务；如果有，就加入当前事务。这就是 PROPAGATION_REQUIRED，它也是 Spring 提供的默认事务传播行为，适合绝大多数情况。
2    如果没有，就新建一个事务；如果有，就将当前事务挂起。这就是 RROPAGATION_REQUIRES_NEW，意思就是创建了一个新事务，它和原来的事务没有任何关系了。
3    如果没有，就新建一个事务；如果有，就在当前事务中嵌套其他事务。这就是 PROPAGATION_NESTED，也就是传说中的“嵌套事务”了，所嵌套的子事务与主事务之间是有关联的（当主事务提交或回滚，子事务也会提交或回滚）。
4    如果没有，就以非事务方式执行；如果有，就使用当前事务。这就是 PROPAGATION_SUPPORTS，这种方式非常随意，没有就没有，有就有，有点无所谓的态度，反正我是支持你的。
5    如果没有，就以非事务方式执行；如果有，就将当前事务挂起。这就是 PROPAGATION_NOT_SUPPORTED，这种方式非常强硬，没有就没有，有我也不支持你，把你挂起来，不鸟你。
6    如果没有，就以非事务方式执行；如果有，就抛出异常。这就是 PROPAGATION_NEVER，这种方式更猛，没有就没有，有了反而报错，确实够牛的，它说：我从不支持事务！
7    如果没有，就抛出异常；如果有，就使用当前事务。这就是 PROPAGATION_MANDATORY，这种方式可以说是牛逼中的牛逼了，没有事务直接就报错，确实够狠的，它说：我必须要有事务！

看到我上面这段解释，小伙伴们是否已经感受到，被打通任督二脉的感觉？多读几遍，体会一下，就是您自己的东西了。

 Spring 给我们带来了事务传播行为，这确实是一个非常强大而又实用的功能。除此以外，也提供了一些小的附加功能，比如：

 1   事务超时【Transaction Timeout】：为了解决事务时间太长，消耗太多的资源，所以故意给事务设置一个最大时常，如果超过了，就回滚事务。
 2   只读事务【Readonly Transaction】：为了忽略那些不需要事务的方法，比如读取数据，这样可以有效地提高一些性能。

最后，推荐大家使用 Spring 的注解式事务配置，而放弃 XML 式事务配置。因为注解实在是太优雅了，当然这一切都取决于您自身的情况了。      
	