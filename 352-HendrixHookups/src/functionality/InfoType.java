package functionality;

public enum InfoType {

	PEOPLE {
		public String getName() {
			return "people";
		}
	}, REQUEST {
		public String getName() {
			return "request";
		}
	}, ACCEPT {
		public String getName() {
			return "accept";
		}
	}, MESSAGE {
		public String getName() {
			return "message";
		}
	}, UPDATE {
		public String getName() {
			return "update";
		}
	};

	abstract public String getName();

}
