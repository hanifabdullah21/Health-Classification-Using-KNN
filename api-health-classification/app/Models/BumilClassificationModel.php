<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Carbon\Carbon;

class BumilClassificationModel extends Model{
    protected $table = 'bumil_classification';

    protected $guarded = [];

    protected $hidden = [];

    public function account(){
        return $this->belongsTo('App\Models\Account', 'account_id');
    }

    public function dusun(){
        return $this->belongsTo('App\Models\Dusun', 'dusun_id');
    }

    public function master() {
        return $this->belongsTo('App\Models\BumilMasterModel', 'bumil_id');
    }
}