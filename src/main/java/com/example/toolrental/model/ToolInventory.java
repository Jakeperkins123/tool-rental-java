package com.example.toolrental.model;

import java.util.HashMap;

public class ToolInventory {
    
    private final HashMap<String, Tool> toolInventory = new HashMap<>();
    
    public void addTool(Tool tool){
        toolInventory.put(tool.getToolCode(), tool);
    }

    public Tool getTool(String toolCode){
        if(!toolInventory.containsKey(toolCode)) throw new IllegalArgumentException(Constants.ERROR_TOOL_NOT_FOUND);
        return toolInventory.get(toolCode);
    }
}