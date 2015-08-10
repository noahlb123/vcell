package cbit.vcell.util;

import java.io.File;

import cbit.vcell.util.AmplistorUtils.AmplistorCredential;

public class UnarchiveAmplistor {
	public static void main(String[] args) {
		try{
		// /share/apps/vcell2/users/boris/SimID_87497385_0__0.simtask.xml
		// /share/apps/vcell2/users/boris/SimID_87497385_0_.fvinput
		// /share/apps/vcell2/users/boris/SimID_87497385_0_.smoldynInput
		// /share/apps/vcell2/users/boris/SimID_87497385_0_.smoldynOutput
		// /share/apps/vcell2/users/boris/SimID_87497385_0_.tid
		// /share/apps/vcell2/users/boris/SimID_87497385_0_.vcg
		String[] fileNames = new String[] {
		"SimID_87497385_0__0.simtask.xml",
		"SimID_87497385_0_.fvinput",
		"SimID_87497385_0_.smoldynInput",
		"SimID_87497385_0_.smoldynOutput",
		"SimID_87497385_0_.tid",
		"SimID_87497385_0_.vcg"
		};
		String amplistorCredentialUser = "vcell";
		String amplistorCredentialCleartextPassword = "cbittech";//put 'camadmin' password here
		AmplistorCredential amplistorCredential = new AmplistorCredential(amplistorCredentialUser, amplistorCredentialCleartextPassword);
		for(String fileName:fileNames){
		//not sure where isolon is anymore
		File destinationFile = new File("\\\\cfs02\\raid\\vcell\\users\\boris\\"+fileName);
		AmplistorUtils.getObjectDataPutInFile(
		AmplistorUtils.DEFAULT_AMPLI_SERVICE_VCELL_URL+"/boris/"+fileName,amplistorCredential, destinationFile);
		}
		}catch(Exception e){
		e.printStackTrace();
		}
		}

}
