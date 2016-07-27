## Service

1. 在Activity中 用 Intent putExtra 给 intent对象 传输给Service
2. onStartCommand 里处理 intent 携带来的数据
3. 只能Service内部使用 不能同步到 Activity 中

---

Activity 和 Service 之间的通信 

1. 使用绑定服务来实现的

2. Service里处理 Binder， 自定义Binder 类， 在 onBind 里处理

3. 在 onStartCommand 里 处理数据的接收

4. 可以在 Service里 实现一个 interface， 暴露出去接口 在本类里 和 被实现的地方 进行通信

5. 在onServiceConnected 中 处理 interface 实现用 Binder 通信

6. 在 Activity 中 使用 Handler 来 捕获数据 和 传输数据

   1. 实现 Handler 的对象  重写 handleMessage 方法
   2. 需要捕获数据的地方 实现 Message对象
   3. 使用message来传输 数据 
   4. 再从handleMessage方法里 处理传过来的 message 对象。

   ​