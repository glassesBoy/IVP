/**
 * 车辆 通用 Model
 */
Ext.define('ivp.model.VehicleModel', {
    extend : 'Ext.data.Model',

    fields : [{
        name : 'id',
        type : 'auto'
    }, {
        name : 'vName',
        type : 'auto'// 
    }, {
        name : 'vCode'
    }, {
        name : 'teamName'
    }, {
        name : 'vLength'
    }, {
        name : 'vWidth'
    }, {
        name : 'vHeight'

    }]
});