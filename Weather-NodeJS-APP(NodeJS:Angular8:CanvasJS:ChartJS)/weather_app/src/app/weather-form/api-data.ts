export class ApiData {
	public city:string;
	public darksky:any;
	public stateseal: string;
	public state: string;
	constructor( c:string, d:any,  ss: string,s :string){
		this.city = c;
		this.darksky = d;
		this.stateseal = ss;
		this.state = s;
	}
}
