package supermarket;

public interface Visitor {
	abstract void visit(BookDepartment bookDepartment);
	abstract void visit(MusicDepartment musicDepartment);
	abstract void visit(SoftwareDepartment softwareDepartment);
	abstract void visit(VideoDepartment videoDepartment);
}
