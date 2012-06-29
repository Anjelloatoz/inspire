package anjello;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;

public class ConstructionSolution implements CaseComponent {
	public enum OrganizationalStrategies { Traditional_Design_Bid_Build, Traditional_Fast_Track, Design_and_Build, Turnkey, Management_Contracting, Partnering, PPP, Prime_Contract, Framework};
	public enum PaymentModalities {Fixed_Price, Measurement, Cost_Reimbursement, Target_Cost, Guaranteed_Maximum_Price};
	
	String id;
	OrganizationalStrategies OrganizationalStrategy;
	PaymentModalities PaymentModality;

	public String getId() {
		return id;
	}

	public void setId(String caseId) {
		this.id = caseId;
	}

	public OrganizationalStrategies getOrganizationalStrategy() {
		return OrganizationalStrategy;
	}

	public void setOrganizationalStrategy(
			OrganizationalStrategies organizationalStrategy) {
		OrganizationalStrategy = organizationalStrategy;
	}

	public PaymentModalities getPaymentModality() {
		return PaymentModality;
	}

	public void setPaymentModality(PaymentModalities paymentModality) {
		PaymentModality = paymentModality;
	}
	
	public String toString()
	{
		return "("+id+";"+id+";"+OrganizationalStrategy+";"+PaymentModality+")";
	}

	@Override
	public Attribute getIdAttribute() {
		// TODO Auto-generated method stub
		return new Attribute("id", this.getClass());
	}

}
