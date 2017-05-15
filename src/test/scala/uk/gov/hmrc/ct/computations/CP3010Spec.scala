package uk.gov.hmrc.ct.computations

import org.mockito.Mockito.when
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpec}
import uk.gov.hmrc.ct.BoxValidationFixture
import uk.gov.hmrc.ct.computations.retriever.ComputationsBoxRetriever

class CP3010Spec extends WordSpec with Matchers with MockitoSugar with BoxValidationFixture[ComputationsBoxRetriever] {

  override val boxRetriever = makeBoxRetriever(true)

  testMandatoryWhen("CP3010", CP3010.apply, validValue = 1)

  testBoxIsZeroOrPositive("CP3010", CP3010.apply)

  testBecauseOfDependendBoxThenCannotExist("CP3010", CP3010.apply) {
    makeBoxRetriever(false)
  }

  private def makeBoxRetriever(cpq321Value: Boolean) = {
    val retriever = mock[ComputationsBoxRetriever]
    when(retriever.cpQ321()).thenReturn(CPQ321(Some(cpq321Value)))
    retriever
  }
}
