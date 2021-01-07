/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.computations.machineryAndPlant

import uk.gov.hmrc.ct.utils.UnitSpec
import org.mockito.Mockito._
import uk.gov.hmrc.ct.computations.{CP666, CP78, CP79, CP82, CP83, CP87, CP88, CPAux1, CPAux2, CPAux3}

class MachineryAndPlantCalculatorLogicSpec extends UnitSpec {


  "MachineryAndPlantCalculatorLogic" should {
    val number1 = 5000
    val number2 = 3000
    val number3 = 2000

    "calculate the value for CP94 successfully" in {
      when(mockComputationsBoxRetriever.cpAux1()) thenReturn CPAux1(number1)
      when(mockComputationsBoxRetriever.cp79()) thenReturn CP79(Some(number2))

      CP94.calculate(mockComputationsBoxRetriever) shouldBe CP94(number1+number2)
    }
    "calculate the value for CP97 successfully" in {
      when(mockComputationsBoxRetriever.cp87()) thenReturn CP87(number2)
      when(mockComputationsBoxRetriever.cp94()) thenReturn CP94(number1)

      CP97.calculate(mockComputationsBoxRetriever) shouldBe CP97(number1-number2)
    }

    "calculate the value for CP105 successfully" in {
      when(mockComputationsBoxRetriever.cpAux2()) thenReturn CPAux2(number1)
      when(mockComputationsBoxRetriever.cp82()) thenReturn CP82(number2)
      when(mockComputationsBoxRetriever.cp78()) thenReturn CP78(number3)

      CP105.calculate(mockComputationsBoxRetriever) shouldBe CP105(number1+number2+number3)
    }

    "calculate the value for CP109 successfully" in {
      when(mockComputationsBoxRetriever.cpAux3()) thenReturn CPAux3(number1)
      when(mockComputationsBoxRetriever.cp666()) thenReturn CP666(number2)

      CP109.calculate(mockComputationsBoxRetriever) shouldBe CP109(number1+number2)
    }

    "calculate the value for CP110 successfully" in {
      when(mockComputationsBoxRetriever.cp83()) thenReturn CP83(number1)
      when(mockComputationsBoxRetriever.cp88()) thenReturn CP88(number2)

      CP110.calculate(mockComputationsBoxRetriever) shouldBe CP110(number1-number2)
    }
  }


}
