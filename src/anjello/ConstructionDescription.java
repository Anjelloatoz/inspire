package anjello;

import jcolibri.cbrcore.Attribute;
import jcolibri.cbrcore.CaseComponent;
import jcolibri.datatypes.Instance;

public class ConstructionDescription implements CaseComponent{
	
	public enum ClientTypes  { Private, Public};
	public enum ClientExperiences { Inexperienced, PartlyExperienced, HighlyExperienced };
	public enum BuildTypes  { GreenField, BrownField, Refurb, Refit};
	public enum CostParameters { LowestCost, HighestCost, CostCertainty };
	public enum TimeParameters { ShortestTime, EarliestCommencement, TimeCertainty };
	
	String  caseId;
	ClientTypes ClientType;
	ClientExperiences ClientExperience;
	Instance  BuildingType;
	BuildTypes BuildType;
	CostParameters CostParameter;
	TimeParameters TimeParameter;
	
	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public ClientTypes getClientType() {
		return ClientType;
	}

	public void setClientType(ClientTypes clientType) {
		ClientType = clientType;
	}

	public ClientExperiences getClientExperience() {
		return ClientExperience;
	}

	public void setClientExperience(ClientExperiences clientExperience) {
		ClientExperience = clientExperience;
	}

	public Instance getBuildingType() {
		return BuildingType;
	}

	public void setBuildingType(Instance buildingType) {
		BuildingType = buildingType;
	}

	public BuildTypes getBuildType() {
		return BuildType;
	}

	public void setBuildType(BuildTypes buildType) {
		BuildType = buildType;
	}

	public CostParameters getCostParameter() {
		return CostParameter;
	}

	public void setCostParameter(CostParameters costParameter) {
		CostParameter = costParameter;
	}

	public TimeParameters getTimeParameter() {
		return TimeParameter;
	}

	public void setTimeParameter(TimeParameters timeParameter) {
		TimeParameter = timeParameter;
	}

	public String toString()
	{
		return "("+caseId+";"+ClientType+";"+ClientExperience+";"+BuildingType+";"+BuildType+";"+CostParameter+";"+TimeParameter+")";
	}

	@Override
	public Attribute getIdAttribute() {
		// TODO Auto-generated method stub
		return new Attribute("caseId", this.getClass());
	}
	
}
