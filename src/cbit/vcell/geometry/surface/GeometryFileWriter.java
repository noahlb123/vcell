package cbit.vcell.geometry.surface;
import java.io.Writer;
import cbit.vcell.geometry.RegionImage;
import cbit.util.ISize;

import java.util.Comparator;
import java.util.Vector;
import java.util.zip.DeflaterOutputStream;
import java.io.ByteArrayOutputStream;
import cbit.vcell.geometry.Geometry;
/**
 * Insert the type's description here.
 * Creation date: (7/19/2004 10:52:10 AM)
 * @author: Jim Schaff
 */
public class GeometryFileWriter {

/**
 * Insert the method's description here.
 * Creation date: (7/19/2004 10:54:30 AM)
 * @param geometrySurfaceDescription cbit.vcell.geometry.surface.GeometrySurfaceDescription
 */
public static int write(Writer writer, Geometry resampledGeometry) throws Exception {
	//
	// "name" name
	// "dimension" dimension
	// "extent" extentx extenty extentz
	// "origin" originx originy originz
	// "volumeRegions" num
	//    name totalVolume featureHandle
	// "membraneRegions" num
	//    name totalArea volumeRegionIndex1 volumeRegionIndex2
	// "volumeSamples" numX, numY, numZ
	//    uncompressed regionIndexs for each volume element
	//    compressed regionIndexs for each volume element
	// "nodes" num
	//    nodeIndex x y z
	// "cells" num
	//    cellIndex patchIndex node1 node2 node3 node4
	// "celldata"
	//    insideVolumeIndex outsideVolumeIndex area normalx normaly normalz
	//
	//
	//When we are writing volume regions, we sort regions so that ID is equal to index
	//	
	writer.write("name "+resampledGeometry.getName()+"\n");
	writer.write("dimension "+resampledGeometry.getDimension()+"\n");
	cbit.util.Extent extent = resampledGeometry.getExtent();
	cbit.util.Origin origin = resampledGeometry.getOrigin();
	switch (resampledGeometry.getDimension()) {
		case 1:			
			writer.write("size "+extent.getX()+"\n");
			writer.write("origin "+origin.getX()+"\n");
			break;
		case 2:
			writer.write("size "+extent.getX()+" "+extent.getY()+"\n");
			writer.write("origin "+origin.getX()+" "+origin.getY()+"\n");
			break;
		case 3:
			writer.write("size "+extent.getX()+" "+extent.getY()+" "+extent.getZ()+"\n");
			writer.write("origin "+origin.getX()+" "+origin.getY()+" "+origin.getZ()+"\n");
			break;
	}	

	GeometrySurfaceDescription geoSurfaceDesc = resampledGeometry.getGeometrySurfaceDescription();
	
	RegionImage regionImage = geoSurfaceDesc.getRegionImage();
	SurfaceCollection surfaceCollection = geoSurfaceDesc.getSurfaceCollection();
	GeometricRegion geometricRegions[] = geoSurfaceDesc.getGeometricRegions();	
	
	int numVolumeRegions = 0;
	int numMembraneRegions = 0;	
	Vector<VolumeGeometricRegion> volRegionList = new Vector<VolumeGeometricRegion>();
	if (geometricRegions != null) {	
		for (int i = 0; i < geometricRegions.length; i++){
			if (geometricRegions[i] instanceof VolumeGeometricRegion){
				numVolumeRegions++;
				volRegionList.add((VolumeGeometricRegion)geometricRegions[i]);
			}else if (geometricRegions[i] instanceof SurfaceGeometricRegion){
				numMembraneRegions++;
			}
		}
	}
	
	//
	// get ordered array of volume regions (where "id" == index into array)... fail if impossible
	//
	java.util.Collections.sort(volRegionList,new Comparator<VolumeGeometricRegion>() {
		public int compare(VolumeGeometricRegion reg1, VolumeGeometricRegion reg2){
			if (reg1.getRegionID()<reg2.getRegionID()){
				return -1;
			}else if (reg1.getRegionID()>reg2.getRegionID()){
				return 1;
			}else{
				return 0;
			}
		}
		public boolean equals(Object obj){
			return this==obj;
		}
	});

	VolumeGeometricRegion volRegions[] = (VolumeGeometricRegion[])cbit.util.BeanUtils.getArray(volRegionList,VolumeGeometricRegion.class);
	
	writer.write("volumeRegions "+numVolumeRegions +"\n");
	for (int i = 0; i < volRegions.length; i++){
		if (volRegions[i].getRegionID() != i) {
			throw new RuntimeException("Region ID != Region Index, they must be the same!");
		}
		writer.write(volRegions[i].getName()+" "+volRegions[i].getSize()+" "+volRegions[i].getSubVolume().getHandle()+"\n");
	}	

	writer.write("membraneRegions "+numMembraneRegions+"\n");
	if (geometricRegions != null) {
		for (int i = 0; i < geometricRegions.length; i++){
			if (geometricRegions[i] instanceof SurfaceGeometricRegion){
				SurfaceGeometricRegion surfaceRegion = (SurfaceGeometricRegion)geometricRegions[i];
				GeometricRegion neighbors[] = surfaceRegion.getAdjacentGeometricRegions();
				VolumeGeometricRegion insideRegion = (VolumeGeometricRegion)neighbors[0];
				VolumeGeometricRegion outsideRegion = (VolumeGeometricRegion)neighbors[1];
				writer.write(surfaceRegion.getName()+" "+surfaceRegion.getSize()+" "+insideRegion.getRegionID()+" "+outsideRegion.getRegionID()+"\n");
			}
		}
	}
	
	//
	// write volume samples
	//
	ISize volumeSampleSize = geoSurfaceDesc.getVolumeSampleSize();
	switch (resampledGeometry.getDimension()) {		
		case 1:
			writer.write("volumeSamples "+volumeSampleSize.getX()+"\n");
			break;
		case 2:
			writer.write("volumeSamples "+volumeSampleSize.getX()+" "+volumeSampleSize.getY()+"\n");
			break;
		case 3:
			writer.write("volumeSamples "+volumeSampleSize.getX()+" "+volumeSampleSize.getY()+" "+volumeSampleSize.getZ()+"\n");
			break;
	}
	
	// regionImage
	if (regionImage != null) {
		if (regionImage.getNumRegions() > 65536){
			throw new RuntimeException("cannot process a geometry with more than 65536 volume regions");
		}
		byte[] uncompressedRegionIDs = new byte[2 * regionImage.getNumX()*regionImage.getNumY()*regionImage.getNumZ()];
		for (int i = 0, j = 0; i < uncompressedRegionIDs.length; i += 2, j ++){
			int regindex = regionImage.getRegionInfoFromOffset(j).getRegionIndex();
			uncompressedRegionIDs[i] = (byte)(regindex & 0x000000ff);
			uncompressedRegionIDs[i + 1] = (byte)((regindex & 0x0000ff00) >> 8);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DeflaterOutputStream dos = new DeflaterOutputStream(bos);
		dos.write(uncompressedRegionIDs,0,uncompressedRegionIDs.length);
		dos.close();
		byte[] compressedRegionIDs = bos.toByteArray();
		writer.write(org.vcell.util.Hex.toString(compressedRegionIDs)+"\n");
	} else {
		writer.write("\n");
	}
		
	//
	// write surfaces
	//
	//SurfaceCollection surfaceCollection = geoSurfaceDesc.getSurfaceCollection();
	/*
	Node nodes[] = surfaceCollection.getNodes();
	int numNodes = nodes.length;
	writer.write("nodes "+numNodes+"\n");
	for (int i = 0; i < nodes.length; i++){
		writer.write(nodes[i].getGlobalIndex()+" "+nodes[i].getX()+" "+nodes[i].getY()+" "+nodes[i].getZ()+"\n");
	}
	*/
	//
	// print the "Cells" (polygons) for each surface (each surface has it's own material id).
	//
	int numCells = 0;
	if (surfaceCollection != null) {
		for (int i = 0; i < surfaceCollection.getSurfaceCount(); i++){
			numCells += surfaceCollection.getSurfaces(i).getPolygonCount();
		}
	}
	writer.write("cells "+numCells+"\n");
	/*
	int cellID = 0;
	for (int i = 0; i < surfaceCollection.getSurfaceCount(); i++){
		Surface surface = surfaceCollection.getSurfaces(i);
		for (int j = 0; j < surface.getPolygonCount(); j++){
			Polygon polygon = surface.getPolygons(j);
			int node0Index = polygon.getNodes(0).getGlobalIndex();
			int node1Index = polygon.getNodes(1).getGlobalIndex();
			int node2Index = polygon.getNodes(2).getGlobalIndex();
			int node3Index = polygon.getNodes(3).getGlobalIndex();
			writer.write(cellID+" "+i+" "+node0Index+" "+node1Index+" "+node2Index+" "+node3Index+"\n");
			cellID++;
		}
	}
	*/
	// "celldata"
	//    insideVolumeIndex outsideVolumeIndex area normalx normaly normalz
	//
	int cellID = 0;
	int dimension = resampledGeometry.getDimension();
	
	double correctCoeff = 1;

	if (dimension == 1) {
		correctCoeff = extent.getY() * extent.getZ();
	} else if (dimension == 2) {
		correctCoeff = extent.getZ();
	}	
	if (surfaceCollection != null) {
		for (int i = 0; i < surfaceCollection.getSurfaceCount(); i++){
			Surface surface = surfaceCollection.getSurfaces(i);
			int region1Outside = 0;
			int region1Inside = 0;
			for (int j = 0; j < surface.getPolygonCount(); j++){
				Quadrilateral polygon = (Quadrilateral)surface.getPolygons(j);
				Node[] node = polygon.getNodes();

				cbit.vcell.render.Vect3d elementCoord = new cbit.vcell.render.Vect3d();
				int nodesOnBoundary = 0;
				for (int k = 0; k < node.length; k ++) {
					if (!node[k].getMoveX() || (dimension > 1 && !node[k].getMoveY()) || (dimension == 3 && !node[k].getMoveZ())) {
						nodesOnBoundary ++;
					}
				}

				if (nodesOnBoundary == 0) {
					for (int k = 0; k < node.length; k ++) {
						elementCoord.add(new cbit.vcell.render.Vect3d(node[k].getX(), node[k].getY(), node[k].getZ()));
					}
					elementCoord.scale(0.25);
				} else if (nodesOnBoundary == 2) {
					for (int k = 0; k < node.length; k ++) {
						if (!node[k].getMoveX() || !node[k].getMoveY() || !node[k].getMoveZ()) {						
							elementCoord.add(new cbit.vcell.render.Vect3d(node[k].getX(), node[k].getY(), node[k].getZ()));							
						}						
					}
					elementCoord.scale(0.5);
				} else if (nodesOnBoundary == 3) {
					for (int k = 0; k < node.length; k ++) {
						if (!node[k].getMoveX() && !node[k].getMoveY() || !node[k].getMoveY() && !node[k].getMoveZ() || !node[k].getMoveX() && !node[k].getMoveZ()) {						
							elementCoord.set(node[k].getX(), node[k].getY(), node[k].getZ());
						}						
					}
				} else {
					throw new RuntimeException("Unexcepted number of nodes on boundary for a polygon: " + nodesOnBoundary);
				}
	
				cbit.vcell.render.Vect3d unitNormal = new cbit.vcell.render.Vect3d();
				polygon.getUnitNormal(unitNormal);
				
				int volNeighbor1Region = regionImage.getRegionInfoFromOffset(polygon.getVolIndexNeighbor1()).getRegionIndex();
				int volNeighbor2Region = regionImage.getRegionInfoFromOffset(polygon.getVolIndexNeighbor2()).getRegionIndex();

				if (surface.getExteriorRegionIndex() == volNeighbor1Region && surface.getInteriorRegionIndex() == volNeighbor2Region) {
					region1Outside ++;
				}
				if (surface.getExteriorRegionIndex() == volNeighbor2Region && surface.getInteriorRegionIndex() == volNeighbor1Region) {
					region1Inside ++;
				}
				
				writer.write(cellID+" "+polygon.getVolIndexNeighbor1()+" "+polygon.getVolIndexNeighbor2()+" "+polygon.getArea()/correctCoeff + " " + elementCoord.getX()+ " " + elementCoord.getY() + " " + elementCoord.getZ() + " " + unitNormal.getX()+ " " + unitNormal.getY() + " " + unitNormal.getZ() + "\n");
				cellID++;
			}
			if (region1Inside != surface.getPolygonCount() && region1Outside != surface.getPolygonCount()) {
				throw new RuntimeException("Volume neighbor regions not consistent: [total, inside, outside]=" + surface.getPolygonCount() + "," + region1Inside + "," + region1Outside + "]");
			}
		}
	}
	return numCells;
}
}