package com.elextec.mdm.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elextec.mdm.common.entity.TableDDLMap;
import com.elextec.mdm.common.entity.VoResponse;
import com.elextec.mdm.entity.ColumnDefinition;
import com.elextec.mdm.entity.TableDefinition;
import com.elextec.mdm.service.ITableDDLService;

@RestController
@RequestMapping("table")
public class TableDDLController {

	@Autowired
	private ITableDDLService tableDDLService;
	
	@GetMapping("getAll")
	public Object getAll(){
		VoResponse voRes = new VoResponse();
		voRes.setData(tableDDLService.getAll());
		return voRes;
	}
	
	@PostMapping
	public Object create(@RequestBody TableDefinition table){
		VoResponse voRes = new VoResponse();
		if(table.getTableName() == null || table.getTableName().equals("")){
			voRes.setNull(voRes);
			voRes.setMessage("tableName is null");
			return voRes;
		}
		List<ColumnDefinition> list = table.getColumnDefinitions();
		for(ColumnDefinition obj : list){
			if(obj.getName() == null || obj.getName().equals("")){
				voRes.setNull(voRes);
				voRes.setMessage("columnName is null");
				return voRes;
			}
			for(Integer key : obj.getDataTypeMap().keySet()){
				if(TableDDLMap.oracleDataTypeMap.get(key) == null){
					voRes.setNull(voRes);
					voRes.setMessage("dataType is null");
					return voRes;
				}
			}
		}
		voRes = tableDDLService.createTable(table);
		return voRes;
	}
	
	@DeleteMapping
	public Object del(@RequestParam("id") String id){
		VoResponse voRes = tableDDLService.dropTable(id);
		return voRes;
	}
	
	@GetMapping("getBasicData")
	public Object getDDLBasicData(){
		VoResponse voRes = new VoResponse();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("DataTypeMap", TableDDLMap.oracleDataTypeMap);
		map.put("ColumnConstraintMap", TableDDLMap.oracleColumnConstraintMap);
		voRes.setData(map);
		return voRes;
	}
}