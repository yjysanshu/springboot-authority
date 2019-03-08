package com.temp.common.util.convert;

import com.temp.permission.entity.Resource;
import com.temp.permission.model.dto.ResourceDTO;
import com.temp.permission.util.JSONUtil;
import org.dozer.DozerConverter;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class DozerResourceConverter extends DozerConverter<Resource, ResourceDTO> {

    public DozerResourceConverter() {
        super(Resource.class, ResourceDTO.class);
    }

    @Override
    public Resource convertFrom(ResourceDTO dto, Resource o) {
        o = new Resource();
        o.setResourceId(dto.getId());
        return o;
    }

    @Override
    public ResourceDTO convertTo(Resource o, ResourceDTO dto) {
        dto = new ResourceDTO();
        dto.setId(o.getResourceId());
        dto.setUrl(o.getResourceTarget());
        dto.setParentId(o.getResourceParentId());

        Map map;
        try {
            map = JSONUtil.jsonToMap(o.getResourceData());
        } catch (IOException e) {
            return dto;
        }
        dto.setTitle((String) map.get("title"));
        dto.setPath((String) map.get("path"));
        dto.setSeq((Integer) map.get("seq"));
        dto.setIcon((String) map.get("icon"));
        dto.setType((String) map.get("type"));
        dto.setChecked(false);
        dto.setChildren(new ArrayList<>());
        return dto;
    }
}
