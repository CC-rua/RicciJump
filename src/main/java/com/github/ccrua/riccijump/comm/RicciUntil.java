package com.github.ccrua.riccijump.comm;

public class RicciUntil {
    public static String transStrToSnakeType(String _selected) {
        _selected = _selected.replaceAll("[A-Z]", "_$0");
        _selected = _selected.toLowerCase();
        if (_selected.charAt(0) == '_') {
            _selected = _selected.substring(1);
        }
        return _selected;

    }

    //驼峰转蛇形
    public String transSnakeToStr(String _selected){
        _selected = _selected.replaceAll("_([A-Z])", "$1");
        int i = -32;

        for(char c = 'a'; c <= 'z'; ++c) {
            _selected = _selected.replaceAll("_" + c, String.valueOf((char)(c + i)));
        }
        return _selected;
    }
}
