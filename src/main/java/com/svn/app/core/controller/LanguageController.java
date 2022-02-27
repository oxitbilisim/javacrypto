package com.svn.app.core.controller;


import com.svn.app.core.entity.Language;
import com.svn.app.core.entity.LanguageKeys;
import com.svn.app.core.repository.LanguageKeysRepository;
import com.svn.app.core.repository.LanguageRepository;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequestMapping("/language")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LanguageController {
    @Autowired
    protected LanguageRepository languageRepository;
    @Autowired
    protected LanguageKeysRepository languageKeysRepository;

    @GetMapping("/list")
    public Collection<Language> list() {
        return new ArrayList<>(languageRepository.findAll());
    }

    @GetMapping("/list/{languageCode}")
    public List<LanguageKeys> getAllKeysByLanguageCode(@PathVariable("languageCode") String languageCode) {
        return languageKeysRepository.findAllByLanguageCode(languageCode);
    }

    @GetMapping("/getRootKeys/{languageCode}")
    public Collection<LanguageKeys> getRootKeys(@PathVariable("languageCode") String languageCode) {
        return new ArrayList<>(languageKeysRepository.findByParentCodeIsNullAndLanguageCodeOrderByKeyCodeAsc(languageCode));
    }

    @GetMapping("/saveFullPath/{languageCode}/{key}/{defaultText}")
    public Boolean saveFullPath(@PathVariable("languageCode") String languageCode, @PathVariable("key") String key, @PathVariable("defaultText") String defaultText) {
        try {
            languageKeysRepository.saveFullPath(languageCode, key, defaultText);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @GetMapping("/getKeysByParent/{parentCode}")
    public Collection<LanguageKeys> getKeysByParent(@PathVariable("parentCode") Integer parentCode) {
        return new ArrayList<>(languageKeysRepository.findByParentCodeOrderByKeyCodeAsc(parentCode));
    }

    @GetMapping("/languageKeys/{languageCode}")
    public String languageKeys(@PathVariable("languageCode") String languageCode) throws Exception {
        JSONObject res = new JSONObject();
        for (LanguageKeys rootItem : languageKeysRepository.findRoots(languageCode)) {
            if (StringUtils.isNotEmpty(rootItem.getDefaultText())) {
                if (rootItem.getDefaultText().split(";").length > 1) {
                    JSONArray jsonArray = new JSONArray();
                    for (String item : rootItem.getDefaultText().split(";"))
                        jsonArray.put(item);
                    res.put(rootItem.getKeyCode(), jsonArray);

                } else
                    res.put(rootItem.getKeyCode(), rootItem.getDefaultText());

            } else {
                res.put(rootItem.getKeyCode(), resursive(languageCode, rootItem, res));
            }
        }
        return res.toString();
    }

    private JSONObject resursive(String languageCode, LanguageKeys parent, JSONObject jsonObject) {
        JSONObject res = new JSONObject();
        for (LanguageKeys item : languageKeysRepository.findByParent(languageCode, parent.getId())) {
            if (StringUtils.isNotEmpty(item.getDefaultText())) {
                if (item.getDefaultText().split(";").length > 1) {
                    JSONArray jsonArray = new JSONArray();
                    for (String tmp : item.getDefaultText().split(";"))
                        jsonArray.put(tmp);
                    res.put(item.getKeyCode(), jsonArray);
                } else
                    res.put(item.getKeyCode(), item.getDefaultText());
            } else {
                res.put(item.getKeyCode(), resursive(languageCode, item, jsonObject));
            }
        }
        return res;
    }

    @PutMapping("/languageKey/{id}")
    LanguageKeys updateLanguageKey(@RequestBody LanguageKeys newLanguageKey, @PathVariable("id") Integer id) {
        return languageKeysRepository.findById(id)
                .map(languageKeys -> {
                    languageKeys.setDefaultText(newLanguageKey.getDefaultText());
                    languageKeys.setKeyCode(newLanguageKey.getKeyCode());
                    return languageKeysRepository.save(languageKeys);
                })
                .orElseGet(() -> {
                    newLanguageKey.setId(id);
                    return languageKeysRepository.save(newLanguageKey);
                });
    }

    @DeleteMapping("/languageKey/{id}")
    public void delete(@PathVariable("id") Integer id) {
        languageKeysRepository.deleteById(id);
    }
/*
    @PostMapping
    Menu add(@RequestBody Menu newMenu) {
        if (newMenu.getParentId() != null)
            newMenu.setParentMenu(fwDao.getLanguageDao().getOne(newMenu.getParentId()));
        return fwDao.getLanguageDao().save(newMenu);
    }


    @GetMapping("/{id}")
    Menu get(@PathVariable("id") Integer id) {
        return fwDao.getLanguageDao().findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        fwDao.getLanguageDao().deleteById(id);
    }

    @PutMapping("/{id}")
    Menu update(@RequestBody Menu newMenu, @PathVariable("id") Integer id) {
        return fwDao.getLanguageDao().findById(id)
                .map(menu -> {
                    menu.setTitle(newMenu.getTitle());
                    menu.setIcon(newMenu.getIcon());
                    menu.setPage(newMenu.getPage());
                    menu.setMenuOrder(newMenu.getMenuOrder());
                    if (newMenu.getParentId() != null)
                        menu.setParentMenu(fwDao.getLanguageDao().getOne(newMenu.getParentId()));
                    menu.setTranslate(newMenu.getTranslate());
                    if (newMenu.getPermission() != null && newMenu.getPermission().getId() != null)
                        menu.setPermission(fwDao.getPermissionDao().getOne(newMenu.getPermission().getId()));
                    else
                        menu.setPermission(null);
                    return fwDao.getLanguageDao().save(menu);
                })
                .orElseGet(() -> {
                    newMenu.setId(id);
                    return fwDao.getLanguageDao().save(newMenu);
                });
    }*/

}
