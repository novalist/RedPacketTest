# RedPacketTest

场景：
1.有两组文件，红包文件和账户文件，每个文件的每一行都是按照`id+空格+金额`排列。
2.红包文件有50个，共一亿行，id会重复。 
3.账户文件有100个，共一千万行，按id%100分开，不重复且保证顺序。 
要做的事： 把红包的金额加到账户文件中，输出到新的文件组（还是100个文件）。
（文件路径可以自己虚拟，不要用hadoop、spark之类的分布式计算框架，单机上用java实现，重点看性能优化的程度）
附件是虚拟的红包和账户文件。




解题思路：
1.将50个红包文件，按照id%100的规则，重新生成reHash的100个文件（结果输出到新路径 Route1）；
（考虑到账户文件的不重复且顺序性，这个步骤是为了方便和账户文件的处理）
2.将100个reHash的文件和100个账户文件进行数据的累加（结果输出到新路径 Route2）。
3.最终获得Route2路径下结果与预期一致。

优化：
1.首先想到的是数据累加的时候开启多线程，因为访问的都是不同的文件；
2.第一步优化后在测试的时候，上述瓶颈在reHash的时候慢，也改造成多线程。

可以继续关注的问题：
1.通过多次输出保存文件到新路径，其实是牺牲空间换时间的做法，或许还有更优的方案；
2.本地环境CPU核数有限，线程池的配置有待提高；
3.在代码编写及抽象角度，还可以有待提高。

时间匆忙，如有没有考虑全的地方，还请指正！
