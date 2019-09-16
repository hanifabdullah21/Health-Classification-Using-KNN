<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Balita extends Model {

    protected $table = 'balita_master';

    protected $guarded = [];

    protected $hidden = ['dusun_id','account_id'];

    public function dusun(){
        return $this->belongsTo('App\Models\Dusun', 'dusun_id');
    }

    public function account(){
        return $this->belongsTo('App\Models\Account', 'account_id');
    }

}