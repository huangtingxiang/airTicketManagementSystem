import json
with open('city.origin.json', 'r', encoding='utf8')as f:
    d = json.load(f)
    data = []
    for key, value in d.items():

        data.append({'name': value, 'pinyin': key, 'primaried': False})

with open('city.json', 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False, indent=4)