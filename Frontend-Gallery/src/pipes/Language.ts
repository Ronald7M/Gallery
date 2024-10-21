type SimpleJson = {
  [key: string]: string | SimpleJson;
};

export class Language {
  public getValue(key: string,json:SimpleJson): string | undefined {
    const keys = key.split('.');
    let result: SimpleJson | string = json;
    for (const k of keys) {
      if (typeof result === 'object' && result !== null && k in result) {
        result = result[k];
      } else {
        return undefined;
      }
    }

    // Returnează rezultatul, care poate fi un string sau undefined
    return typeof result === 'string' ? result : undefined;
  }

  protected ro: SimpleJson = {
    'login':{
      'username':'Nume de utilizator',
      'password':'Parola',
      'submit':'Intra '
    },
    'hello': 'ro1',
    'welcome': 'ro2',
    'json': {
      'first': 'dasdsad',
      'json2': {
        'first2': 'dasdsad7675765',
      }
    }

  };

  protected en: SimpleJson = {
    'login':{
      'username':'Username',
      'password':'Password',
      'submit':'Submit'
    },
    'hello': 'ro1',
    'welcome': 'ro2',
    'json': {
      'first': 'dasdsad',
      'json2': {
        'first2': 'jkkljljk',
      }
    },
    'upload':{
      'choseDirector':'Chose the Director'
    },
    'dir':{
      'create':'Create',
      'directorName':'Name director',
      'newDirector':'Create new director'
    }
  };
}
