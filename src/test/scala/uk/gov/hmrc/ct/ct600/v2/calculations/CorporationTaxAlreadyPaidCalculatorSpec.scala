/*
 * Copyright 2021 HM Revenue & Customs
 *
 */

package uk.gov.hmrc.ct.ct600.v2.calculations

import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.ct600.v2.{B86, B91, B92, B93}

class CorporationTaxAlreadyPaidCalculatorSpec extends WordSpec with Matchers {

  "corporationTaxOutstanding" should {

    "should return zero when the amount already paid is greater than the amount owed" in new Calc {
      corporationTaxOutstanding(B86(100), B91(200)) shouldBe B92(0)
    }

    "should return the correct amount when when the amount already paid is less than the amount owed" in new Calc {
      corporationTaxOutstanding(B86(100), B91(75)) shouldBe B92(25)
    }

    "should return the correct amount when when the amount already paid is less than the amount owed, with decimal values" in new Calc {
      corporationTaxOutstanding(B86(BigDecimal("100.50")), B91(BigDecimal("100.45"))) shouldBe B92(BigDecimal("0.05"))
    }

    "should return zero when the amount already paid is equal to the amount owed" in new Calc {
      corporationTaxOutstanding(B86(100), B91(100)) shouldBe B92(0)
    }
  }

  "corporationTaxOverpaid" should {

    "should return the correct amount when the amount already paid is greater than the amount owed" in new Calc {
      corporationTaxOverpaid(B86(100), B91(125)) shouldBe B93(25)
    }

    "should return the correct amount when when the amount already paid is greater than the amount owed, with decimal values" in new Calc {
      corporationTaxOverpaid(B86(BigDecimal("100.45")), B91(BigDecimal("100.50"))) shouldBe B93(BigDecimal("0.05"))
    }

    "should return zero correct amount when when the amount already paid is less than the amount owed" in new Calc {
      corporationTaxOverpaid(B86(100), B91(75)) shouldBe B93(0)
    }

    "should return zero when the amount already paid is equal to the amount owed" in new Calc {
      corporationTaxOverpaid(B86(100), B91(100)) shouldBe B93(0)
    }
  }

  class Calc extends CorporationTaxAlreadyPaidCalculator
}
