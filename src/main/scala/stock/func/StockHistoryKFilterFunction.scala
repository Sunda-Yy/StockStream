package stock.func

import org.apache.flink.api.common.functions.RichFilterFunction
import org.apache.flink.api.common.state.ValueStateDescriptor
import stock.bean.StockHistoryK

class StockHistoryKFilterFunction extends RichFilterFunction[StockHistoryK]{

  //这个获取上下文，是要获取之前的时间状态的参数
  lazy val tsState = getRuntimeContext.getState(new ValueStateDescriptor[String]("tsState",classOf[String],""))

  override def filter(value :StockHistoryK): Boolean = {
    if (tsState.value().equals("") || value.ts >tsState.value()){
      tsState.update(value.ts)
      true
    }else{
      false
    }

  }
}
