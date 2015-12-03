package org.vcell.vis.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ChomboFiles {
	
	class ChomboFileEntry {
		private final String volumeDomainName;
		private final int timeIndex;
		private final File file;
		
		public ChomboFileEntry(String volumeDomainName, int timeIndex, File file) {
			super();
			this.volumeDomainName = volumeDomainName;
			this.timeIndex = timeIndex;
			this.file = file;
		}

		public String getVolumeDomainName() {
			return volumeDomainName;
		}

		public int getTimeIndex() {
			return timeIndex;
		}

		public File getFile() {
			return file;
		}

	}
	
	private final String simID;
	private final int jobIndex;
	private final File meshFile;
	private ArrayList<ChomboFileEntry> dataFiles = new ArrayList<ChomboFileEntry>();
	
	public ChomboFiles(String simID, int jobIndex, File meshFile) {
		super();
		this.simID = simID;
		this.jobIndex = jobIndex;
		this.meshFile = meshFile;
	}
	
	public int getJobIndex(){
		return this.jobIndex;
	}
	
	public String getSimID(){
		return this.simID;
	}

	public File getMeshFile() {
		return meshFile;
	}
	
	public void addDataFile(String domainName, int timeIndex, File file){
		dataFiles.add(new ChomboFileEntry(domainName,timeIndex,file));
	}

	public Set<String> getVolumeDomainNames() {
		HashSet<String> domainNames = new HashSet<String>();
		for (ChomboFileEntry entry : dataFiles){
			domainNames.add(entry.volumeDomainName);
		}
		return domainNames;
	}
	
	public List<File> getDataFilesFromVolumeDomainName(String volumeDomainName){
		ArrayList<File> domainDataFiles = new ArrayList<File>();
		for (ChomboFileEntry entry : dataFiles){
			if (entry.volumeDomainName.equals(volumeDomainName)){
				domainDataFiles.add(entry.file);
			}
		}
		java.util.Collections.sort(domainDataFiles,new Comparator<File>(){
			public int compare(File o1, File o2) {
				return o1.getPath().compareTo(o2.getPath());
			}
		});
		return domainDataFiles;
	}

	public File getDataFileFromVolumeDomainName(String volumeDomainName, int timeIndex) {
		for (ChomboFileEntry entry : dataFiles){
			if (entry.volumeDomainName.equals(volumeDomainName) && entry.timeIndex==timeIndex){
				return entry.file;
			}
		}
		throw new RuntimeException("no data found for domain '"+volumeDomainName+"' and timeIndex "+timeIndex);
	}

	public List<Integer> getTimeIndices() {
		ArrayList<Integer> timeIndices = new ArrayList<Integer>();
		for (ChomboFileEntry entry : dataFiles){
			if (!timeIndices.contains(entry.timeIndex)){
				timeIndices.add(entry.timeIndex);
			}
		}
		java.util.Collections.sort(timeIndices,new Comparator<Integer>(){
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		});
		return timeIndices;
	}
	
	public List<ChomboFileEntry> getEntries(){
		return dataFiles;
	}

	public String getCannonicalFilePrefix(String domainName, int timeIndex) {
		return getSimID()+"_"+getJobIndex()+"_"+domainName+"_"+String.format("%06d",timeIndex);
	}

	public String getCannonicalFilePrefix(String domainName) {
		return getSimID()+"_"+getJobIndex()+"_"+domainName;
	}

}
