<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Account extends Model {

    protected $table = 'accounts';

    protected $guarded = [];

    protected $hidden = [
        'password'
    ];

    public function balita(){
        return $this->hasMany('App\Models\Balita', 'account_id');
    }

    public function balitaClassification(){
        return $this->hasMany('App\Models\BalitaClassificationModel', 'account_id');
    }
}
